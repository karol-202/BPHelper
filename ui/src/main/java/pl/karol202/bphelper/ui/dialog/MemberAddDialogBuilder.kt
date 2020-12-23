package pl.karol202.bphelper.ui.dialog

import android.content.Context
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedAlertDialog
import pl.karol202.bphelper.ui.databinding.DialogMemberAddBinding
import pl.karol202.bphelper.ui.extensions.addAfterTextChangedListener
import pl.karol202.bphelper.ui.extensions.setNegativeButton
import pl.karol202.bphelper.ui.extensions.setPositiveButton
import pl.karol202.bphelper.ui.extensions.viewBinding

class MemberAddDialogBuilder(context: Context,
                             private val nameValidator: (String) -> Validity,
                             private val onApply: (String) -> Unit) : ExtendedAlertDialog(context)
{
	enum class Validity
	{
		VALID, EMPTY
	}

	private val views by viewBinding(DialogMemberAddBinding::inflate)

	private val currentName get() = views.editMemberName.text.toString()

	init
	{
		setTitle(R.string.alert_add_member_title)
		setView(views.root)
		initButtons()
		initEditText()
	}

	private fun initButtons()
	{
		setPositiveButton(R.string.action_add) { _, _ -> checkAndApply() }
		setNegativeButton(R.string.action_cancel, null)
	}

	private fun initEditText()
	{
		views.editMemberName.addAfterTextChangedListener { updateNameValidity(it) }
	}

	private fun updateNameValidity(name: String)
	{
		views.textInputLayoutMemberName.error = when(nameValidator(name))
		{
			Validity.VALID -> null
			Validity.EMPTY -> context.getString(R.string.text_member_name_empty_error)
		}
	}

	private fun checkAndApply()
	{
		updateNameValidity(currentName)
		if(nameValidator(currentName) != Validity.VALID) return
		onApply(currentName)
		dismiss()
	}
}
