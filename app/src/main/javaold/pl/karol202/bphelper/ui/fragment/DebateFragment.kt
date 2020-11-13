package pl.karol202.bphelper.ui.fragment

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_debate.*
import pl.karol202.bphelper.*
import pl.karol202.bphelper.components.ExtendedFragment
import pl.karol202.bphelper.extensions.alertDialog
import pl.karol202.bphelper.extensions.format
import pl.karol202.bphelper.extensions.getColorCompat
import pl.karol202.bphelper.service.DebateRecorderService
import pl.karol202.bphelper.ui.dialog.RecordingFilenameDialogBuilder
import pl.karol202.bphelper.ui.dialog.recordingFilenameDialog
import java.io.File
import kotlin.math.roundToInt
import kotlin.time.Duration

class DebateFragment : ExtendedFragment(), TimerStateContext, RecordingStateContext, OnRecordingStopListener
{
	companion object
	{
		private const val DIRECTORY_RECORDINGS = "Recordings"
	}

	override val onRecordingStopListener = this

	private lateinit var bellPlayer: BellPlayer
	private val drawablePlayToPause by lazy { AnimatedVectorDrawableCompat.create(ctx, R.drawable.anim_play_to_pause_white_24dp) }
	private val drawablePauseToPlay by lazy { AnimatedVectorDrawableCompat.create(ctx, R.drawable.anim_pause_to_play_white_24dp) }

	private var _timerState by instanceStateOr<TimerState>(TimerStateStopped.create(this))
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

		buttonDebateTimeStart.setOnClickListener { onTimerStartButtonClick() }
		buttonDebateTimeStop.setOnClickListener { onTimerStopButtonClick() }
		buttonDebateRecording.setOnClickListener { toggleRecording() }
	}

	private fun onTimerStartButtonClick()
	{
		when(val previousState = timerState)
		{
			is TimerStateRunning -> timerState =
				TimerStatePaused.create(this, previousState.getElapsedTime(), previousState.getTimeChecks())
			is TimerStatePaused -> timerState =
				TimerStateRunning.create(this, previousState.elapsedTime, previousState.timeChecks)
			is TimerStateStopped -> timerState = TimerStateRunning.create(this)
		}
	}

	private fun onTimerStopButtonClick()
	{
		timerState = TimerStateStopped.create(this)
	}

	private fun toggleRecording()
	{
		if(recordingState is RecordingStateEnabled) showRecordingStopAlert()
		else if(recordingState is RecordingStateDisabled) showFilenameAlertIfPermitted()
	}

	private fun showFilenameAlertIfPermitted()
	{
		fun checkAndRequestAudioPermission(): Boolean
		{
			val hasPermission = checkPermission(Manifest.permission.RECORD_AUDIO)
			if(!hasPermission) requestPermission(Manifest.permission.RECORD_AUDIO) { granted ->
				if(granted) showFilenameAlertIfPermitted()
			}
			return hasPermission
		}

		fun checkStorageState() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

		fun checkAndRequestStoragePermission(): Boolean
		{
			val hasPermission = checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
			if(!hasPermission) requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) { granted ->
				if(granted) showFilenameAlertIfPermitted()
			}
			return hasPermission
		}

		fun showExternalStorageErrorMessage()
		{
			Snackbar.make(view ?: return, R.string.text_recording_storage_error, Snackbar.LENGTH_SHORT).show()
		}

		fun getFileForRecording(filename: String): File
		{
			val recordingsDirectory = File(ctx.getExternalFilesDir(null), DIRECTORY_RECORDINGS)
			recordingsDirectory.mkdirs()
			return File(recordingsDirectory, "$filename${DebateRecorderService.FILENAME_SUFFIX}")
		}

		if(!checkAndRequestAudioPermission()) return
		if(!checkStorageState()) return showExternalStorageErrorMessage()
		if(!checkAndRequestStoragePermission()) return
		ctx.recordingFilenameDialog {
			setFilenameValidityChecker { filename -> when
			{
				filename.isEmpty() -> RecordingFilenameDialogBuilder.Validity.EMPTY
				getFileForRecording(filename).exists() -> RecordingFilenameDialogBuilder.Validity.BUSY
				else -> RecordingFilenameDialogBuilder.Validity.VALID
			} }
			setOnFilenameSetListener { filename ->
				val file = getFileForRecording(filename)
				setRecordingEnabled(filename, file)
			}
		}.show()
	}

	private fun showRecordingStopAlert()
	{
		ctx.alertDialog {
			setTitle(R.string.alert_recording_stop_title)
			setPositiveButton(R.string.action_finish) { _, _ -> setRecordingDisabled() }
			setNegativeButton(R.string.action_cancel, null)
		}.show()
	}

	private fun setRecordingEnabled(filename: String, file: File)
	{
		recordingState = RecordingStateEnabled.create(this@DebateFragment, filename, file)
	}

	private fun setRecordingDisabled()
	{
		recordingState = RecordingStateDisabled.create(this@DebateFragment)
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
		val color = if(overtime) R.color.text_timer_overtime else R.color.color_primary
		textDebateTimer.setTextColor(ctx.getColorCompat(color))
	}

	override fun updateTimerButtons()
	{
		fun setStartButtonConstraints(stopped: Boolean)
		{
			val constraints = ConstraintSet()
			constraints.clone(constraintLayoutDebate)

			if(stopped) constraints.connect(R.id.buttonDebateTimeStart, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
			else constraints.connect(R.id.buttonDebateTimeStart, ConstraintSet.END, R.id.buttonDebateTimeStop, ConstraintSet.START)

			val margin = ctx.resources.getDimension(if(stopped) R.dimen.margin_button_debate_start_stopped
			                                        else R.dimen.margin_button_debate_start_running).roundToInt()
			constraints.setMargin(R.id.buttonDebateTimeStart, ConstraintSet.END, margin)

			constraints.applyTo(constraintLayoutDebate)
		}

		TransitionManager.beginDelayedTransition(view as ViewGroup)
		when(timerState)
		{
			is TimerStateRunning -> {
				setStartButtonConstraints(stopped = false)
				if(buttonDebateTimeStart.icon != drawablePlayToPause)
					buttonDebateTimeStart.icon = drawablePlayToPause?.apply { start() }
				buttonDebateTimeStart.setText(R.string.button_debate_timer_pause)
				buttonDebateTimeStop.isClickable = true
			}
			is TimerStatePaused -> {
				setStartButtonConstraints(stopped = false)
				if(buttonDebateTimeStart.icon != drawablePauseToPlay)
					buttonDebateTimeStart.icon = drawablePauseToPlay?.apply { start() }
				buttonDebateTimeStart.setText(R.string.button_debate_timer_resume)
				buttonDebateTimeStop.isClickable = true
			}
			is TimerStateStopped -> {
				setStartButtonConstraints(stopped = true)
				if(buttonDebateTimeStart.icon != drawablePauseToPlay)
					buttonDebateTimeStart.icon = drawablePauseToPlay?.apply { start() }
				buttonDebateTimeStart.setText(R.string.button_debate_timer_start)
				buttonDebateTimeStop.isClickable = false
			}
		}
	}

	override fun playBellSound(times: Int) = bellPlayer.play(times)

	override fun updateRecordingButton(text: Int)
	{
		buttonDebateRecording.setText(text)
	}

	//Will be called unless recording was stopped by button
	override fun onRecordingStop(error: Boolean, filename: String?)
	{
		fun showRecordingStopMessage(error: Boolean, filename: String?)
		{
			val message = if(!error) getString(R.string.text_recording_saved, filename)
			else getString(R.string.text_recording_error)
			Snackbar.make(view ?: return, message, Snackbar.LENGTH_LONG).show()
		}

		if(context == null) return
		setRecordingDisabled()
		showRecordingStopMessage(error, filename)
	}
}
