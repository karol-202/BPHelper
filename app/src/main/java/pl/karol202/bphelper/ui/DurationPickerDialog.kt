package pl.karol202.bphelper.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_duration_picker.*
import pl.karol202.bphelper.Duration
import pl.karol202.bphelper.R

class DurationPickerDialog(context: Context,
                           private val onDurationSetListener: ((duration: Duration) -> Unit)? = null,
                           initialDuration: Duration = Duration.zero) : ExtendedAlertDialog(context), LayoutContainer
{
	@SuppressLint("InflateParams")
	override val containerView: View = LayoutInflater.from(context).inflate(R.layout.dialog_duration_picker, null)

	private var currentMinutes by instanceStateOr(initialDuration.minutes)
	private var currentSeconds by instanceStateOr(initialDuration.seconds)

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
		numberPickerMinutes.setFormatter { String.format("%02d", it) }
		numberPickerMinutes.minValue = 0
		numberPickerMinutes.maxValue = 59
		numberPickerMinutes.value = currentMinutes
		numberPickerMinutes.setOnValueChangedListener { _, _, newValue -> currentMinutes = newValue }

		numberPickerSeconds.setFormatter { String.format("%02d", it) }
		numberPickerSeconds.minValue = 0
		numberPickerSeconds.maxValue = 59
		numberPickerSeconds.value = currentSeconds
		numberPickerSeconds.setOnValueChangedListener { _, _, newValue -> currentSeconds = newValue }
	}

	private fun initButtons()
	{
		setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.action_ok)) { _, _ ->
			onDurationSetListener?.invoke(getDuration())
		}
		setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.action_cancel)) { _, _ -> }
	}

	private fun getDuration() = Duration.create(numberPickerMinutes.value, numberPickerSeconds.value)
		?: throw IllegalStateException("Cannot create duration")
}