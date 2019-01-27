package pl.karol202.bphelper.ui

import android.content.Context
import androidx.annotation.StyleRes
import kotlinx.android.synthetic.main.dialog_member_add.*
import pl.karol202.bphelper.R

fun Context.memberAddDialog(@StyleRes style: Int = 0, init: MemberAddDialogBuilder.() -> Unit) =
	MemberAddDialogBuilder(this, style).apply(init)

class MemberAddDialogBuilder(context: Context, @StyleRes style: Int = 0) :
	CustomDialogBuilder(context, R.layout.dialog_member_add, style)
{
	private var onAddListener: ((String) -> Unit)? = null

	init
	{
		setTitle(R.string.alert_add_member_title)
		setPositiveButton(pl.karol202.bphelper.R.string.action_add) { _, _ ->
			onAddListener?.invoke(editMemberName.text.toString())
		}
		setNegativeButton(pl.karol202.bphelper.R.string.action_cancel, null)
	}

	fun setOnAddListener(listener: ((String) -> Unit)?)
	{
		onAddListener = listener
	}
}