package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_prep_timer.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedFragment
import pl.karol202.bphelper.ui.dialog.fragment.DurationPickerFragment
import pl.karol202.bphelper.ui.extensions.collectIn
import pl.karol202.bphelper.ui.extensions.ctx
import pl.karol202.bphelper.ui.extensions.format
import pl.karol202.bphelper.ui.viewmodel.AndroidPrepTimerViewModel
import kotlin.time.Duration

class PrepTimerFragment : ExtendedFragment(), DurationPickerFragment.OnDurationSetListener
{
	private val prepTimerViewModel by sharedViewModel<AndroidPrepTimerViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_prep_timer, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		observeTimerValue()
		observeTimerActive()
		observeValueSetDialogResponse()

		initTimerText()
		initToggleButton()
	}

	private fun observeTimerValue() = prepTimerViewModel.timerValue.collectIn(lifecycleScope) { value ->
		text_prep_timer.text = value.format(ctx)
	}

	private fun observeTimerActive() = prepTimerViewModel.timerActive.collectIn(lifecycleScope) { active ->
		button_prep_timer_start.setText(if(active) R.string.button_prep_timer_stop else R.string.button_prep_timer_start)
	}

	private fun observeValueSetDialogResponse() = prepTimerViewModel.valueSetDialogResponse.collectIn(lifecycleScope) {
		DurationPickerFragment.create(it.initialValue, this).show(parentFragmentManager)
	}

	private fun initTimerText() = text_prep_timer.setOnClickListener { prepTimerViewModel.requestValueSetDialog() }

	private fun initToggleButton() = button_prep_timer_start.setOnClickListener { prepTimerViewModel.toggle() }

	override fun onDurationSet(duration: Duration) = prepTimerViewModel.setDuration(duration)
}
