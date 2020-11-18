package pl.karol202.bphelper.ui.fragment

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_debate.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pl.karol202.bphelper.presentation.util.collectIn
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedFragment
import pl.karol202.bphelper.ui.extensions.format
import pl.karol202.bphelper.ui.extensions.getColorCompat
import pl.karol202.bphelper.ui.viewmodel.AndroidDebateViewModel
import pl.karol202.bphelper.ui.viewmodel.AndroidMembersViewModel
import java.io.File
import kotlin.math.roundToInt
import kotlin.time.Duration

class DebateFragment : ExtendedFragment()//, TimerStateContext, RecordingStateContext, OnRecordingStopListener
{
	private val debateViewModel by sharedViewModel<AndroidDebateViewModel>()

	private val drawablePlayToPause by lazy {
		AnimatedVectorDrawableCompat.create(ctx, R.drawable.anim_play_to_pause_white_24dp)
	}
	private val drawablePauseToPlay by lazy {
		AnimatedVectorDrawableCompat.create(ctx, R.drawable.anim_pause_to_play_white_24dp)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_debate, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		observeTimerValue()
		observeTimerStatus()
		observeTimerOvertime()

		initButtons()
	}

	private fun observeTimerValue() = debateViewModel.timerValue.collectIn(lifecycleScope) { value ->
		text_debate_timer.text = value.format(ctx)
	}

	private fun observeTimerStatus() = debateViewModel.timerStatus.collectIn(lifecycleScope) { status ->
		updateTimerButtons(status)
	}

	private fun observeTimerOvertime() = debateViewModel.overtime.collectIn(lifecycleScope) { overtime ->
		val color = if(overtime) R.color.text_timer_overtime else R.color.color_primary
		text_debate_timer.setTextColor(ctx.getColorCompat(color))
	}

	private fun initButtons()
	{
		button_debate_time_start.setOnClickListener { debateViewModel.toggle() }
		button_debate_time_stop.setOnClickListener { debateViewModel.reset() }
		//button_debate_recording.setOnClickListener { toggleRecording() }
	}

	private fun updateTimerButtons(status: DebateViewModel.TimerStatus)
	{
		fun setStartButtonConstraints(stopped: Boolean)
		{
			val constraints = ConstraintSet()
			constraints.clone(constraintLayout_debate)

			if(stopped) constraints.connect(R.id.button_debate_time_start, ConstraintSet.END,
			                                ConstraintSet.PARENT_ID, ConstraintSet.END)
			else constraints.connect(R.id.button_debate_time_start, ConstraintSet.END,
			                         R.id.button_debate_time_stop, ConstraintSet.START)

			val margin = ctx.resources.getDimension(if(stopped) R.dimen.margin_button_debate_start_stopped
			                                        else R.dimen.margin_button_debate_start_running).roundToInt()
			constraints.setMargin(R.id.button_debate_time_start, ConstraintSet.END, margin)

			constraints.applyTo(constraintLayout_debate)
		}

		TransitionManager.beginDelayedTransition(view as ViewGroup)
		when(status)
		{
			DebateViewModel.TimerStatus.ACTIVE -> {
				setStartButtonConstraints(stopped = false)
				if(button_debate_time_start.icon != drawablePlayToPause)
					button_debate_time_start.icon = drawablePlayToPause?.apply { start() }
				button_debate_time_start.setText(R.string.button_debate_timer_pause)
				button_debate_time_stop.isClickable = true
			}
			DebateViewModel.TimerStatus.PAUSED -> {
				setStartButtonConstraints(stopped = false)
				if(button_debate_time_start.icon != drawablePauseToPlay)
					button_debate_time_start.icon = drawablePauseToPlay?.apply { start() }
				button_debate_time_start.setText(R.string.button_debate_timer_resume)
				button_debate_time_stop.isClickable = true
			}
			DebateViewModel.TimerStatus.STOPPED -> {
				setStartButtonConstraints(stopped = true)
				if(button_debate_time_start.icon != drawablePauseToPlay)
					button_debate_time_start.icon = drawablePauseToPlay?.apply { start() }
				button_debate_time_start.setText(R.string.button_debate_timer_start)
				button_debate_time_stop.isClickable = false
			}
		}
	}

	/*companion object
	{
		private const val DIRECTORY_RECORDINGS = "Recordings"
	}

	override val onRecordingStopListener = this

	private var _recordingState by instanceStateOr<RecordingState>(RecordingStateDisabled.create(this))
	private var recordingState: RecordingState
		get() = _recordingState
		set(value)
		{
			_recordingState.onExiting()
			_recordingState = value
			_recordingState.onEntering()
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
		timerState.onExiting()
		recordingState.onExiting()
	}

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
	}*/
}
