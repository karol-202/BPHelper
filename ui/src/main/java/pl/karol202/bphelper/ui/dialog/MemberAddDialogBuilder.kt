package pl.karol202.bphelper.ui.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_member_add.*
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.extensions.addAfterTextChangedListener

class MemberAddDialogBuilder(context: Context,
                             private val nameValidator: (String) -> Validity,
                             private val onApply: (String) -> Unit) :
	CustomDialogBuilder(context, R.layout.dialog_member_add)
{
	enum class Validity
	{
		VALID, EMPTY
	}

	init
	{
		initDialog()
		initEditText()
	}

	private fun initDialog()
	{
		setTitle(R.string.alert_add_member_title)
		setPositiveButton(R.string.action_add, null)
		setNegativeButton(R.string.action_cancel, null)
	}

	private fun initEditText()
	{
		edit_member_name.addAfterTextChangedListener { updateNameValidity(it) }
	}

	private fun updateNameValidity(name: String)
	{
		textInputLayout_member_name.error = when(nameValidator(name))
		{
			Validity.VALID -> null
			Validity.EMPTY -> context.getString(R.string.text_member_name_empty_error)
		}
	}

	override fun show(): AlertDialog = super.show().apply {
		getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { checkAndApply(this) }
	}

	private fun checkAndApply(dialog: AlertDialog)
	{
		val name = edit_member_name.text.toString()
		if(nameValidator(name) != Validity.VALID) return
		onApply(name)
		dialog.dismiss()
	}
}
