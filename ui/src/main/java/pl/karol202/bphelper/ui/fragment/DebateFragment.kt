package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_debate.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pl.karol202.bphelper.presentation.viewdata.RecordingEventViewData
import pl.karol202.bphelper.presentation.viewdata.RecordingStatusViewData
import pl.karol202.bphelper.presentation.viewdata.TimerStatusViewData
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedFragment
import pl.karol202.bphelper.ui.dialog.RecordingNameDialogBuilder
import pl.karol202.bphelper.ui.extensions.*
import pl.karol202.bphelper.ui.viewmodel.AndroidDebateViewModel
import kotlin.math.roundToInt

class DebateFragment : ExtendedFragment()
{
	private val debateViewModel by sharedViewModel<AndroidDebateViewModel>()

	private val drawablePlayToPause by lazy {
		AnimatedVectorDrawableCompat.create(ctx, R.drawable.anim_play_to_pause_white_24dp)
	}
	private val drawablePauseToPlay by lazy {
		AnimatedVectorDrawableCompat.create(ctx, R.drawable.anim_pause_to_play_white_24dp)
	}
	private val buttonsTransition by lazy {
		TransitionInflater.from(ctx).inflateTransition(R.transition.debate_buttons)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_debate, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		observeTimerValue()
		observeTimerStatus()
		observeTimerOvertime()
		observeRecordingStatus()
		observeRecordingEvent()
		observeDialogResponse()

		initButtons()
	}

	private fun observeTimerValue() = debateViewModel.timerValue.collectIn(lifecycleScope) { value ->
		text_debate_timer.text = value.format(ctx)
	}

	private fun observeTimerStatus() = debateViewModel.timerStatus.collectIn(lifecycleScope) { status ->
		updateTimerButtons(status)
	}

	private fun observeTimerOvertime() = debateViewModel.timerOvertime.collectIn(lifecycleScope) { overtime ->
		val color = if(overtime) R.color.text_timer_overtime else R.color.color_primary
		text_debate_timer.setTextColor(ctx.getColorCompat(color))
	}

	private fun observeRecordingStatus() = debateViewModel.recordingStatus.collectIn(lifecycleScope) { status ->
		button_debate_recording.setText(when(status)
		{
			RecordingStatusViewData.ACTIVE -> R.string.button_debate_recording_disable
			RecordingStatusViewData.STOPPED -> R.string.button_debate_recording_enable
		})
	}

	private fun observeRecordingEvent() = debateViewModel.recordingEvent.collectIn(lifecycleScope) { event ->
		showRecordingEventSnackbar(event)
	}

	private fun observeDialogResponse() = debateViewModel.dialogResponse.collectIn(lifecycleScope) {
		when(it)
		{
			is DebateViewModel.DialogResponse.RecordingStopDialogResponse -> showRecordingStopAlert()
			is DebateViewModel.DialogResponse.FilenameChooseDialogResponse -> showFilenameAlert()
		}
	}

	private fun initButtons()
	{
		button_debate_time_toggle.setOnClickListener { debateViewModel.toggleTimer() }
		button_debate_time_stop.setOnClickListener { debateViewModel.resetTimer() }
		button_debate_recording.setOnClickListener { debateViewModel.requestRecordingToggle() }
	}

	private fun showRecordingStopAlert()
	{
		alertDialog {
			setTitle(R.string.alert_recording_stop_title)
			setPositiveButton(R.string.action_finish) { _, _ -> debateViewModel.stopRecording() }
			setNegativeButton(R.string.action_cancel, null)
		}.show()
	}

	private fun showFilenameAlert()
	{
		RecordingNameDialogBuilder(
			context = ctx,
			nameValidator = { debateViewModel.checkRecordingNameValidity(it) },
			onApply = { debateViewModel.startRecording(it) }
		).show()
	}

	private fun showRecordingEventSnackbar(event: RecordingEventViewData)
	{
		val message = when(event)
		{
			RecordingEventViewData.FINISH -> R.string.text_recording_saved
			RecordingEventViewData.ERROR -> R.string.text_recording_error
		}
		showSnackbar(message, Snackbar.LENGTH_LONG)
	}

	private fun updateTimerButtons(status: TimerStatusViewData)
	{
		fun setStartButtonConstraints(stopped: Boolean)
		{
			val constraints = ConstraintSet()
			constraints.clone(constraintLayout_debate)

			if(stopped) constraints.connect(R.id.button_debate_time_toggle, ConstraintSet.END,
			                                ConstraintSet.PARENT_ID, ConstraintSet.END)
			else constraints.connect(R.id.button_debate_time_toggle, ConstraintSet.END,
			                         R.id.button_debate_time_stop, ConstraintSet.START)

			val margin = ctx.resources.getDimension(if(stopped) R.dimen.margin_button_debate_start_stopped
			                                        else R.dimen.margin_button_debate_start_running).roundToInt()
			constraints.setMargin(R.id.button_debate_time_toggle, ConstraintSet.END, margin)

			constraints.applyTo(constraintLayout_debate)
		}

		TransitionManager.beginDelayedTransition(view as ViewGroup, buttonsTransition)
		when(status)
		{
			TimerStatusViewData.ACTIVE -> {
				setStartButtonConstraints(stopped = false)
				if(button_debate_time_toggle.icon != drawablePlayToPause)
					button_debate_time_toggle.icon = drawablePlayToPause?.apply { start() }
				button_debate_time_toggle.setText(R.string.button_debate_timer_pause)
				button_debate_time_stop.isClickable = true
				button_debate_time_stop.visibility = View.VISIBLE
			}
			TimerStatusViewData.PAUSED -> {
				setStartButtonConstraints(stopped = false)
				if(button_debate_time_toggle.icon != drawablePauseToPlay)
					button_debate_time_toggle.icon = drawablePauseToPlay?.apply { start() }
				button_debate_time_toggle.setText(R.string.button_debate_timer_resume)
				button_debate_time_stop.isClickable = true
				button_debate_time_stop.visibility = View.VISIBLE
			}
			TimerStatusViewData.STOPPED -> {
				setStartButtonConstraints(stopped = true)
				if(button_debate_time_toggle.icon != drawablePauseToPlay)
					button_debate_time_toggle.icon = drawablePauseToPlay?.apply { start() }
				button_debate_time_toggle.setText(R.string.button_debate_timer_start)
				button_debate_time_stop.isClickable = false
				button_debate_time_stop.visibility = View.GONE
			}
		}
	}
}
