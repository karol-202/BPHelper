package pl.karol202.bphelper.ui.settings

import android.view.View
import android.widget.NumberPicker
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedPreferenceDialogFragmentCompat
import pl.karol202.bphelper.ui.extensions.FragmentArgument
import pl.karol202.bphelper.ui.extensions.setArguments
import kotlin.time.minutes
import kotlin.time.seconds

class DurationPreferenceDialogFragment : ExtendedPreferenceDialogFragmentCompat()
{
	companion object
	{
		fun create(key: String) = DurationPreferenceDialogFragment().setArguments(FragmentArgument(ARG_KEY, key))
	}

	private val durationPreference by lazy { preference as DurationPreference }
	private val initialDuration by lazy { durationPreference.duration }
	private var currentMinutes by instanceStateOr { initialDuration.inMinutes.toInt() }
	private var currentSeconds by instanceStateOr { initialDuration.inSeconds.toInt() % 60 }

	override fun onBindDialogView(view: View)
	{
		super.onBindDialogView(view)
		initNumberPickers(view)
	}

	private fun initNumberPickers(view: View)
	{
		initNumberPicker(view.findViewById(R.id.numberPickerMinutes), currentMinutes) { currentMinutes = it }
		initNumberPicker(view.findViewById(R.id.numberPickerSeconds), currentSeconds) { currentSeconds = it }
	}

	private fun initNumberPicker(pickerView: NumberPicker, currentValue: Int, valueSetter: (Int) -> Unit) {
		pickerView.setFormatter { String.format("%02d", it) }
		pickerView.minValue = 0
		pickerView.maxValue = 59
		pickerView.value = currentValue
		pickerView.setOnValueChangedListener { _, _, newValue -> valueSetter(newValue) }
	}

	override fun onDialogClosed(positiveResult: Boolean)
	{
		if(!positiveResult) return
		val newDuration = currentMinutes.minutes + currentSeconds.seconds
		if(durationPreference.callChangeListener(newDuration))
			durationPreference.duration = newDuration
	}
}
