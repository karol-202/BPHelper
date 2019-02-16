package pl.karol202.bphelper.members

import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_member_add.*
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.CustomDialogBuilder

fun Context.memberAddDialog(@StyleRes style: Int = 0, init: MemberAddDialogBuilder.() -> Unit) =
	MemberAddDialogBuilder(this, style).apply(init)

class MemberAddDialogBuilder(context: Context, @StyleRes style: Int = 0) :
	CustomDialogBuilder(context, R.layout.dialog_member_add, style)
{
	enum class Validity
	{
		VALID, EMPTY, BUSY
	}

	private var nameValidityChecker: ((String) -> Validity)? = null
	private var onAddListener: ((String) -> Unit)? = null

	init
	{
		initEditText()
		initDialog()
	}

	private fun initEditText()
	{
		editMemberName.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

			override fun afterTextChanged(s: Editable?) = updateNameValidity(s.toString())
		})
	}

	private fun updateNameValidity(name: String)
	{
		textInputLayoutMemberName.error = when(checkNameValidity(name))
		{
			Validity.VALID -> null
			Validity.EMPTY -> context.getString(R.string.text_member_name_empty_error)
			Validity.BUSY -> context.getString(R.string.text_member_name_busy_error)
		}
	}

	private fun checkNameValidity(name: String) = nameValidityChecker?.invoke(name) ?: Validity.VALID

	private fun initDialog()
	{
		setTitle(R.string.alert_add_member_title)
		setPositiveButton(R.string.action_add, null)
		setNegativeButton(R.string.action_cancel, null)
	}

	override fun show(): AlertDialog
	{
		val dialog = super.show()
		dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { checkAndApply(dialog) }
		return dialog
	}

	private fun checkAndApply(dialog: AlertDialog)
	{
		val name = editMemberName.text.toString()
		if(checkNameValidity(name) != Validity.VALID) return
		onAddListener?.invoke(name)
		dialog.dismiss()
	}

	fun setNameValidityChecker(checker: (String) -> Validity)
	{
		nameValidityChecker = checker
		updateNameValidity(editMemberName.text.toString())
	}

	fun setOnAddListener(listener: ((String) -> Unit)?)
	{
		onAddListener = listener
	}
}