package pl.karol202.bphelper.debate

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_debate.*
import pl.karol202.bphelper.Duration
import pl.karol202.bphelper.R
import pl.karol202.bphelper.components.ExtendedFragment
import pl.karol202.bphelper.extensions.alertDialog
import pl.karol202.bphelper.extensions.getColorCompat
import pl.karol202.bphelper.minus
import pl.karol202.bphelper.orThrow
import pl.karol202.bphelper.settings.Settings
import java.io.File

private interface TimerStateContext
{
	val ctx: Context

	fun updateClock(duration: Duration)

	fun updateClockOvertime(overtime: Boolean)

	fun updateTimerButton(@StringRes text: Int)

	fun playBellSound(times: Int)
}

private interface RecordingStateContext
{
	val ctx: Context

	fun updateRecordingButton(@StringRes text: Int)
}

class FragmentDebate : ExtendedFragment(), TimerStateContext, RecordingStateContext
{
	companion object
	{
		private const val DIRECTORY_RECORDINGS = "Recordings"

		private val TICK_INTERVAL = Duration.create(millis = 100)!!
	}

	private interface TimerState : Parcelable
	{
		fun setContext(stateContext: TimerStateContext)

		fun onEntering()

		fun onExiting()
	}

	@Parcelize
	private class TimerStateEnabled private constructor(private var elapsedTime: Duration = Duration.zero,
	                                                    private val timeChecks: TimeChecks = TimeChecks()) : TimerState
	{
		companion object
		{
			fun create(stateContext: TimerStateContext) = TimerStateEnabled().apply { setContext(stateContext) }
		}

		private inner class Timer(elapsedTime: Duration,
		                          tickInterval: Duration) :
			CountDownTimer((Duration.max - elapsedTime).orThrow().timeInMillis.toLong(), tickInterval.timeInMillis.toLong())
		{
			override fun onTick(millisUntilFinished: Long) = onTimerUpdate((Duration.max - Duration.fromMillis(millisUntilFinished.toInt())).orThrow())

			override fun onFinish() = onTimerFinish()
		}

		@Parcelize
		data class TimeChecks(var speechDuration: Boolean = false,
		                      var speechDurationMax: Boolean = false,
		                      var poiStart: Boolean = false,
		                      var poiEnd: Boolean = false) : Parcelable

		@IgnoredOnParcel private lateinit var stateContext: TimerStateContext
		@IgnoredOnParcel private var timer: Timer? = null

		override fun setContext(stateContext: TimerStateContext)
		{
			this.stateContext = stateContext
		}

		override fun onEntering()
		{
			timer = Timer(elapsedTime, TICK_INTERVAL).apply { start() }
			stateContext.updateClock(elapsedTime)
			stateContext.updateClockOvertime(elapsedTime >= Settings.speechDuration)
			stateContext.updateTimerButton(R.string.button_debate_timer_stop)
		}

		override fun onExiting()
		{
			timer?.cancel()
			timer = null

			stateContext.updateClockOvertime(false)
		}

		fun onTimerUpdate(elapsedTime: Duration)
		{
			this.elapsedTime = elapsedTime
			stateContext.updateClock(elapsedTime)
			checkTime()
		}

		private fun checkTime()
		{
			if(!timeChecks.poiStart && elapsedTime >= Settings.poiStart && Settings.poiStartEnabled)
			{
				timeChecks.poiStart = true
				stateContext.playBellSound(1)
			}
			if(!timeChecks.poiEnd && elapsedTime >= Settings.poiEnd && Settings.poiEndEnabled)
			{
				timeChecks.poiEnd = true
				stateContext.playBellSound(1)
			}
			if(!timeChecks.speechDuration && elapsedTime >= Settings.speechDuration && Settings.speechDuration != Duration.zero)
			{
				timeChecks.speechDuration = true
				stateContext.updateClockOvertime(true)
				stateContext.playBellSound(2)
			}
			if(!timeChecks.speechDurationMax && elapsedTime >= Settings.speechDurationMax &&Settings.speechDurationMax != Duration.zero)
			{
				timeChecks.speechDurationMax = true
				stateContext.playBellSound(3)
			}
		}

		//When timer reaches 59:59.999
		fun onTimerFinish() { }
	}

	@Parcelize
	private class TimerStateDisabled private constructor() : TimerState
	{
		companion object
		{
			fun create(stateContext: TimerStateContext) = TimerStateDisabled().apply { setContext(stateContext) }
		}

		@IgnoredOnParcel private lateinit var stateContext: TimerStateContext

		override fun setContext(stateContext: TimerStateContext)
		{
			this.stateContext = stateContext
		}

		override fun onEntering()
		{
			stateContext.updateClock(Duration.zero)
			stateContext.updateTimerButton(R.string.button_debate_timer_start)
		}

		override fun onExiting() { }
	}

	private interface RecordingState : Parcelable
	{
		fun setContext(stateContext: RecordingStateContext)

		fun onEntering()

		fun onExiting()
	}

	@Parcelize
	private class RecordingStateEnabled private constructor(val filename: String,
	                                                        val file: File) : RecordingState
	{
		companion object
		{
			fun create(stateContext: RecordingStateContext, filename: String, file: File) =
				RecordingStateEnabled(filename, file).apply { setContext(stateContext) }
		}

		@IgnoredOnParcel private lateinit var stateContext: RecordingStateContext

		override fun setContext(stateContext: RecordingStateContext)
		{
			this.stateContext = stateContext
		}

		override fun onEntering()
		{
			DebateRecorderService.start(stateContext.ctx, file.absolutePath)

			stateContext.updateRecordingButton(R.string.button_debate_recording_disable)
		}

		override fun onExiting()
		{
			DebateRecorderService.stop(stateContext.ctx)
		}
	}

	@Parcelize
	private class RecordingStateDisabled private constructor(): RecordingState
	{
		companion object
		{
			fun create(stateContext: RecordingStateContext) =
				RecordingStateDisabled().apply { setContext(stateContext) }
		}

		@IgnoredOnParcel private lateinit var stateContext: RecordingStateContext

		override fun setContext(stateContext: RecordingStateContext)
		{
			this.stateContext = stateContext
		}

		override fun onEntering()
		{
			stateContext.updateRecordingButton(R.string.button_debate_recording_enable)
		}

		override fun onExiting() { }
	}

	override val ctx: Context
		get() = requireContext()

	private lateinit var bellPlayer: BellPlayer

	private var _timerState by instanceStateOr<TimerState>(TimerStateDisabled.create(this))
	private var timerState: TimerState
		get() = _timerState
		set(value)
		{
			_timerState.onExiting()
			_timerState = value
			_timerState.onEntering()
		}

	private var _recordingState by instanceStateOr<RecordingState>(RecordingStateDisabled.create(this))
	private var recordingState: RecordingState
		get() = _recordingState
		set(value)
		{
			_recordingState.onExiting()
			_recordingState = value
			_recordingState.onEntering()
		}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_debate, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		bellPlayer = BellPlayer(ctx)
		timerState.setContext(this)
		timerState.onEntering()
		recordingState.setContext(this)
		recordingState.onEntering()

		buttonDebateTime.setOnClickListener { toggleTimerState() }
		buttonDebateRecording.setOnClickListener { toggleRecording() }
	}

	private fun toggleTimerState()
	{
		if(timerState is TimerStateEnabled) timerState = TimerStateDisabled.create(this)
		else if(timerState is TimerStateDisabled) timerState = TimerStateEnabled.create(this)
	}

	private fun toggleRecording()
	{
		if(recordingState is RecordingStateEnabled) showRecordingStopAlert()
		else if(recordingState is RecordingStateDisabled) showFilenameAlertIfPermitted()
	}

	private fun showFilenameAlertIfPermitted()
	{
		if(!checkAndRequestAudioPermission()) return
		if(!checkStorageState()) return showExternalStorageErrorMessage()
		if(!checkAndRequestStoragePermission()) return
		ctx.recordingFilenameDialog {
			setFilenameValidityChecker { filename -> !getFileForRecording(filename).exists() }
			setOnFilenameSetListener { filename ->
				val file = getFileForRecording(filename)
				recordingState = RecordingStateEnabled.create(this@FragmentDebate, filename, file)
			}
		}.show()
	}

	private fun checkAndRequestAudioPermission(): Boolean
	{
		val hasPermission = checkPermission(Manifest.permission.RECORD_AUDIO)
		if(!hasPermission) requestPermission(Manifest.permission.RECORD_AUDIO) { granted ->
			if(granted) showFilenameAlertIfPermitted()
		}
		return hasPermission
	}

	private fun checkStorageState() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

	private fun checkAndRequestStoragePermission(): Boolean
	{
		val hasPermission = checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
		if(!hasPermission) requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) { granted ->
			if(granted) showFilenameAlertIfPermitted()
		}
		return hasPermission
	}

	private fun showExternalStorageErrorMessage()
	{
		Snackbar.make(view ?: return, R.string.text_recording_storage_error, Snackbar.LENGTH_SHORT).show()
	}

	private fun getFileForRecording(filename: String): File
	{
		val recordingsDirectory = File(Environment.getExternalStorageDirectory(), DIRECTORY_RECORDINGS)
		recordingsDirectory.mkdirs()
		return File(recordingsDirectory, filename + DebateRecorderService.FILENAME_SUFFIX)
	}

	private fun showRecordingStopAlert()
	{
		ctx.alertDialog {
			setTitle(R.string.alert_recording_stop_title)
			setPositiveButton(R.string.action_finish) { _, _ ->
				(recordingState as? RecordingStateEnabled)?.let { showRecordingStopMessage(it) }
				recordingState = RecordingStateDisabled.create(this@FragmentDebate)
			}
			setNegativeButton(R.string.action_cancel, null)
		}.show()
	}

	private fun showRecordingStopMessage(recordingState: RecordingStateEnabled)
	{
		val message = getString(R.string.text_recording_saved, recordingState.filename)
		Snackbar.make(view ?: return, message, Snackbar.LENGTH_LONG).show()
	}

	override fun onDestroyView()
	{
		super.onDestroyView()
		bellPlayer.release()
		timerState.onExiting()
		recordingState.onExiting()
	}

	override fun updateClock(duration: Duration)
	{
		textDebateTimer.text = duration.format(ctx)
	}

	override fun updateClockOvertime(overtime: Boolean)
	{
		val color = if(overtime) R.color.text_timer_overtime else R.color.colorAccent
		textDebateTimer.setTextColor(ctx.getColorCompat(color))
	}

	override fun updateTimerButton(text: Int)
	{
		buttonDebateTime.setText(text)
	}

	override fun playBellSound(times: Int) = bellPlayer.play(times)

	override fun updateRecordingButton(text: Int)
	{
		buttonDebateRecording.setText(text)
	}
}