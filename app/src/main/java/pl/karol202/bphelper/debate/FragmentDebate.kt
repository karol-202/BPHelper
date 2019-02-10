package pl.karol202.bphelper.debate

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_debate.*
import pl.karol202.bphelper.Duration
import pl.karol202.bphelper.R
import pl.karol202.bphelper.components.ExtendedFragment
import pl.karol202.bphelper.extensions.alertDialog
import pl.karol202.bphelper.extensions.getColorCompat
import java.io.File

class FragmentDebate : ExtendedFragment(), TimerStateContext, RecordingStateContext, OnRecordingStopListener
{
	companion object
	{
		private const val DIRECTORY_RECORDINGS = "Recordings"
	}

	override val ctx: Context
		get() = requireContext()
	override val onRecordingStopListener = this

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
		return File(recordingsDirectory, "$filename${DebateRecorderService.FILENAME_SUFFIX}")
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
		recordingState = RecordingStateEnabled.create(this@FragmentDebate, filename, file)
	}

	private fun setRecordingDisabled()
	{
		recordingState = RecordingStateDisabled.create(this@FragmentDebate)
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

	//Will be called unless recording was stopped by button
	override fun onRecordingStop(error: Boolean, filename: String?)
	{
		if(context == null) return
		setRecordingDisabled()
		showRecordingStopMessage(error, filename)
	}

	private fun showRecordingStopMessage(error: Boolean, filename: String?)
	{
		val message = if(!error) getString(R.string.text_recording_saved, filename)
					  else getString(R.string.text_recording_error)
		Snackbar.make(view ?: return, message, Snackbar.LENGTH_LONG).show()
	}
}
