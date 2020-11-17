package pl.karol202.bphelper.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_duration_picker.*
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedAlertDialog
import kotlin.time.Duration
import kotlin.time.minutes
import kotlin.time.seconds

class DurationPickerDialog(context: Context,
                           private val onDurationSetListener: ((duration: Duration) -> Unit)? = null,
                           initialDuration: Duration = Duration.ZERO) : ExtendedAlertDialog(context),
                                                                        LayoutContainer
{
	@SuppressLint("InflateParams")
	override val containerView: View = LayoutInflater.from(context).inflate(R.layout.dialog_duration_picker, null)

	private var currentMinutes by instanceStateOr { initialDuration.inMinutes.toInt() }
	private var currentSeconds by instanceStateOr { initialDuration.inSeconds.toInt() % 60 }

	private val numberPickerMinutes = (this as LayoutContainer).numberPickerMinutes
	private val numberPickerSeconds = (this as LayoutContainer).numberPickerSeconds

	init
	{
		setTitle(R.string.alert_set_prep_time)
		setView(containerView)
		initButtons()
	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		initNumberPickers()
	}

	private fun initNumberPickers()
	{
		initNumberPicker(numberPickerMinutes, currentMinutes) { currentMinutes = it }
		initNumberPicker(numberPickerSeconds, currentSeconds) { currentSeconds = it }
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
		setButton(BUTTON_POSITIVE, context.getString(R.string.action_ok)) { _, _ -> onDurationSetListener?.invoke(getDuration()) }
		setButton(BUTTON_NEGATIVE, context.getString(R.string.action_cancel)) { _, _ -> }
	}

	private fun getDuration() = numberPickerMinutes.value.minutes + numberPickerSeconds.value.seconds
}
