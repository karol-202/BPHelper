package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedFragment
import pl.karol202.bphelper.ui.components.viewBinding
import pl.karol202.bphelper.ui.databinding.FragmentPrepTimerBinding
import pl.karol202.bphelper.ui.dialog.fragment.DurationPickerFragment
import pl.karol202.bphelper.ui.extensions.collectIn
import pl.karol202.bphelper.ui.extensions.ctx
import pl.karol202.bphelper.ui.extensions.format
import pl.karol202.bphelper.ui.extensions.viewBinding
import pl.karol202.bphelper.ui.viewmodel.AndroidPrepTimerViewModel
import kotlin.time.Duration

class PrepTimerFragment : ExtendedFragment(R.layout.fragment_prep_timer), DurationPickerFragment.OnDurationSetListener
{
	private val prepTimerViewModel by sharedViewModel<AndroidPrepTimerViewModel>()

	private val views by viewBinding(FragmentPrepTimerBinding::bind)

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
		views.textPrepTimer.text = value.format(ctx)
	}

	private fun observeTimerActive() = prepTimerViewModel.timerActive.collectIn(lifecycleScope) { active ->
		views.buttonPrepTimerStart.setText(if(active) R.string.button_prep_timer_stop else R.string.button_prep_timer_start)
	}

	private fun observeValueSetDialogResponse() = prepTimerViewModel.valueSetDialogResponse.collectIn(lifecycleScope) {
		DurationPickerFragment.create(title = getString(R.string.alert_set_prep_time),
		                              initialDuration = it.initialValue,
		                              onDurationSetListener = this).show(parentFragmentManager)
	}

	private fun initTimerText() = views.textPrepTimer.setOnClickListener { prepTimerViewModel.requestValueSetDialog() }

	private fun initToggleButton() = views.buttonPrepTimerStart.setOnClickListener { prepTimerViewModel.toggle() }

	override fun onDurationSet(duration: Duration) = prepTimerViewModel.setDuration(duration)
}
