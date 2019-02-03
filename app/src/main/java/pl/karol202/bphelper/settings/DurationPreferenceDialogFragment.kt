package pl.karol202.bphelper.settings

import android.view.View
import android.widget.NumberPicker
import pl.karol202.bphelper.Duration
import pl.karol202.bphelper.R
import pl.karol202.bphelper.components.ExtendedPreferenceDialogFragmentCompat
import pl.karol202.bphelper.extensions.FragmentArgument
import pl.karol202.bphelper.extensions.setArguments
import pl.karol202.bphelper.orThrow

class DurationPreferenceDialogFragment : ExtendedPreferenceDialogFragmentCompat()
{
	companion object
	{
		fun create(key: String) = DurationPreferenceDialogFragment().setArguments(FragmentArgument(ARG_KEY, key))
	}

	private val durationPreference by lazy { preference as DurationPreference }
	private val initialDuration by lazy { durationPreference.duration }
	private var currentMinutes by instanceStateOr { initialDuration.minutes }
	private var currentSeconds by instanceStateOr { initialDuration.seconds }

	override fun onBindDialogView(view: View)
	{
		super.onBindDialogView(view)
		initNumberPickers(view)
	}

	private fun initNumberPickers(view: View)
	{
		val numberPickerMinutes = view.findViewById<NumberPicker>(R.id.numberPickerMinutes)
		numberPickerMinutes.setFormatter { String.format("%02d", it) }
		numberPickerMinutes.minValue = 0
		numberPickerMinutes.maxValue = 59
		numberPickerMinutes.value = currentMinutes
		numberPickerMinutes.setOnValueChangedListener { _, _, newValue -> currentMinutes = newValue }

		val numberPickerSeconds = view.findViewById<NumberPicker>(R.id.numberPickerSeconds)
		numberPickerSeconds.setFormatter { String.format("%02d", it) }
		numberPickerSeconds.minValue = 0
		numberPickerSeconds.maxValue = 59
		numberPickerSeconds.value = currentSeconds
		numberPickerSeconds.setOnValueChangedListener { _, _, newValue -> currentSeconds = newValue }
	}

	override fun onDialogClosed(positiveResult: Boolean)
	{
		if(!positiveResult) return
		val newDuration = Duration.create(minutes = currentMinutes, seconds = currentSeconds).orThrow()
		if(durationPreference.callChangeListener(newDuration))
			durationPreference.duration = newDuration
	}
}