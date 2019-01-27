package pl.karol202.bphelper

import android.content.Context
import android.widget.EditText
import androidx.annotation.StyleRes
import kotlinx.android.synthetic.main.dialog_member_add.*

fun Context.memberAddDialog(@StyleRes style: Int = 0, init: MemberAddDialogBuilder.() -> Unit) =
	MemberAddDialogBuilder(this, style).apply(init)

class MemberAddDialogBuilder(context: Context, @StyleRes style: Int = 0) :
	CustomDialogBuilder(context, R.layout.dialog_member_add, style)
{
	val editTextMemberName: EditText
		get() = editMemberName

	init
	{
		setTitle(R.string.alert_add_member_title)
	}
}