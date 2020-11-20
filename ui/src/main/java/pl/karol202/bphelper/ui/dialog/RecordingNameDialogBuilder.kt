package pl.karol202.bphelper.ui.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_recording_name.*
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.extensions.addAfterTextChangedListener

class RecordingNameDialogBuilder(context: Context,
                                 private val nameValidator: (String) -> Validity,
                                 private val onApply: (String) -> Unit) :
	CustomDialogBuilder(context, R.layout.dialog_recording_name)
{
	enum class Validity
	{
		VALID, EMPTY, BUSY
	}

	init
	{
		initEditText()
		initDialog()
	}

	private fun initEditText() = edit_recording_name.addAfterTextChangedListener { updateNameValidity(it) }

	private fun updateNameValidity(name: String)
	{
		textInputLayout_recording_name.error = when(nameValidator(name))
		{
			Validity.VALID -> null
			Validity.EMPTY -> context.getString(R.string.text_filename_empty)
			Validity.BUSY -> context.getString(R.string.text_filename_busy)
		}
	}

	private fun initDialog()
	{
		setTitle(R.string.alert_recording_name_title)
		setPositiveButton(R.string.action_record, null)
		setNegativeButton(R.string.action_cancel, null)
	}

	override fun show() = super.show().apply {
		getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { checkAndApply(this) }
	}

	private fun checkAndApply(dialog: AlertDialog)
	{
		val name = edit_recording_name.text.toString()
		if(nameValidator(name) != Validity.VALID) return
		onApply(name)
		dialog.dismiss()
	}
}
