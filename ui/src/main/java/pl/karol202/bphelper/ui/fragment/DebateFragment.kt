package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pl.karol202.bphelper.presentation.viewdata.RecordingEventViewData
import pl.karol202.bphelper.presentation.viewdata.RecordingStatusViewData
import pl.karol202.bphelper.presentation.viewdata.TimerStatusViewData
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedFragment
import pl.karol202.bphelper.ui.components.viewBinding
import pl.karol202.bphelper.ui.databinding.FragmentDebateBinding
import pl.karol202.bphelper.ui.dialog.RecordingNameDialogBuilder
import pl.karol202.bphelper.ui.extensions.*
import pl.karol202.bphelper.ui.viewmodel.AndroidDebateViewModel
import kotlin.math.roundToInt

class DebateFragment : ExtendedFragment(R.layout.fragment_debate)
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

	private val views by viewBinding(FragmentDebateBinding::bind)

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
		views.textDebateTimer.text = value.format(ctx)
	}

	private fun observeTimerStatus() = debateViewModel.timerStatus.collectIn(lifecycleScope) { status ->
		updateTimerButtons(status)
	}

	private fun observeTimerOvertime() = debateViewModel.timerOvertime.collectIn(lifecycleScope) { overtime ->
		val color = if(overtime) R.color.text_timer_overtime else R.color.color_primary
		views.textDebateTimer.setTextColor(ctx.getColorCompat(color))
	}

	private fun observeRecordingStatus() = debateViewModel.recordingStatus.collectIn(lifecycleScope) { status ->
		views.buttonDebateRecording.setText(when(status)
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
		views.buttonDebateTimeToggle.setOnClickListener { debateViewModel.toggleTimer() }
		views.buttonDebateTimeStop.setOnClickListener { debateViewModel.resetTimer() }
		views.buttonDebateRecording.setOnClickListener { debateViewModel.requestRecordingToggle() }
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
		fun update(stopped: Boolean)
		{
			val constraints = ConstraintSet()
			constraints.clone(views.constraintLayoutDebate)

			if(stopped) constraints.connect(R.id.button_debate_time_toggle, ConstraintSet.END,
			                                ConstraintSet.PARENT_ID, ConstraintSet.END)
			else constraints.connect(R.id.button_debate_time_toggle, ConstraintSet.END,
			                         R.id.button_debate_time_stop, ConstraintSet.START)

			val margin = ctx.resources.getDimension(if(stopped) R.dimen.margin_button_debate_start_stopped
			                                        else R.dimen.margin_button_debate_start_running).roundToInt()
			constraints.setMargin(R.id.button_debate_time_toggle, ConstraintSet.END, margin)

			constraints.applyTo(views.constraintLayoutDebate)

			views.buttonDebateTimeStop.isClickable = !stopped
			views.buttonDebateTimeStop.visibility = if(stopped) View.GONE else View.VISIBLE
		}

		TransitionManager.beginDelayedTransition(view as ViewGroup, buttonsTransition)
		when(status)
		{
			TimerStatusViewData.ACTIVE -> {
				update(stopped = false)
				if(views.buttonDebateTimeToggle.icon != drawablePlayToPause)
					views.buttonDebateTimeToggle.icon = drawablePlayToPause?.apply { start() }
				views.buttonDebateTimeToggle.setText(R.string.button_debate_timer_pause)

			}
			TimerStatusViewData.PAUSED -> {
				update(stopped = false)
				if(views.buttonDebateTimeToggle.icon != drawablePauseToPlay)
					views.buttonDebateTimeToggle.icon = drawablePauseToPlay?.apply { start() }
				views.buttonDebateTimeToggle.setText(R.string.button_debate_timer_resume)
			}
			TimerStatusViewData.STOPPED -> {
				update(stopped = true)
				if(views.buttonDebateTimeToggle.icon != drawablePauseToPlay)
					views.buttonDebateTimeToggle.icon = drawablePauseToPlay?.apply { start() }
				views.buttonDebateTimeToggle.setText(R.string.button_debate_timer_start)
			}
		}
	}
}
