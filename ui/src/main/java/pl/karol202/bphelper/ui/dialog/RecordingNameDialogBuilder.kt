package pl.karol202.bphelper.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import pl.karol202.bphelper.presentation.viewdata.RecordingNameValidityViewData
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedAlertDialog
import pl.karol202.bphelper.ui.databinding.DialogRecordingNameBinding
import pl.karol202.bphelper.ui.extensions.addAfterTextChangedListener
import pl.karol202.bphelper.ui.extensions.setNegativeButton
import pl.karol202.bphelper.ui.extensions.setPositiveButton
import pl.karol202.bphelper.ui.extensions.viewBinding

class RecordingNameDialogBuilder(context: Context,
                                 private val nameValidator: (String) -> RecordingNameValidityViewData,
                                 private val onApply: (String) -> Unit) : ExtendedAlertDialog(context)
{
	private val views by viewBinding(DialogRecordingNameBinding::inflate)

	private val currentName get() = views.editRecordingName.text.toString()

	init
	{
		setTitle(R.string.alert_recording_name_title)
		setView(views.root)
		initButtons()
		initEditText()
	}

	private fun initButtons()
	{
		setPositiveButton(R.string.action_record) { _, _ -> checkAndApply() }
		setNegativeButton(R.string.action_cancel, null)
	}

	private fun initEditText() = views.editRecordingName.addAfterTextChangedListener { updateNameValidity(it) }

	private fun updateNameValidity(name: String)
	{
		views.textInputLayoutRecordingName.error = when(nameValidator(name))
		{
			RecordingNameValidityViewData.VALID -> null
			RecordingNameValidityViewData.EMPTY -> context.getString(R.string.text_filename_empty)
			RecordingNameValidityViewData.BUSY -> context.getString(R.string.text_filename_busy)
		}
	}

	private fun checkAndApply()
	{
		updateNameValidity(currentName)
		if(nameValidator(currentName) != RecordingNameValidityViewData.VALID) return
		onApply(currentName)
		dismiss()
	}
}
