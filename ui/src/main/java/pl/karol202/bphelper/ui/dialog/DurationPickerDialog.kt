package pl.karol202.bphelper.ui.dialog

import android.content.Context
import android.os.Bundle
import android.widget.NumberPicker
import androidx.annotation.StringRes
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedAlertDialog
import pl.karol202.bphelper.ui.databinding.DialogDurationPickerBinding
import pl.karol202.bphelper.ui.extensions.setNegativeButton
import pl.karol202.bphelper.ui.extensions.setPositiveButton
import pl.karol202.bphelper.ui.extensions.viewBinding
import kotlin.time.Duration
import kotlin.time.minutes
import kotlin.time.seconds

class DurationPickerDialog(context: Context,
                           title: String?,
                           initialDuration: Duration = Duration.ZERO,
                           private val onDurationSetListener: ((duration: Duration) -> Unit)? = null) :
	ExtendedAlertDialog(context)
{
	private var currentMinutes by instanceStateOr { initialDuration.inMinutes.toInt() }
	private var currentSeconds by instanceStateOr { initialDuration.inSeconds.toInt() % 60 }

	private val views by viewBinding(DialogDurationPickerBinding::inflate)

	init
	{
		setTitle(title)
		setView(views.root)
		initButtons()
		initNumberPickers()
	}

	private fun initNumberPickers()
	{
		initNumberPicker(views.numberPickerMinutes, currentMinutes) { currentMinutes = it }
		initNumberPicker(views.numberPickerSeconds, currentSeconds) { currentSeconds = it }
	}

	private fun initNumberPicker(pickerView: NumberPicker, currentValue: Int, valueSetter: (Int) -> Unit) {
		pickerView.setFormatter { String.format("%02d", it) }
		pickerView.minValue = 0
		pickerView.maxValue = 59
		pickerView.value = currentValue
		pickerView.setOnValueChangedListener { _, _, newValue -> valueSetter(newValue) }
	}

	private fun initButtons()
	{
		setPositiveButton(R.string.action_ok) { _, _ -> onDurationSetListener?.invoke(getDuration()) }
		setNegativeButton(R.string.action_cancel, null)
	}

	private fun getDuration() = views.numberPickerMinutes.value.minutes + views.numberPickerSeconds.value.seconds
}
