package pl.karol202.bphelper.ui.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView

val View.ctx: Context get() = context

fun TextView.addAfterTextChangedListener(block: (String) -> Unit) = addTextChangedListener(object : TextWatcher {
	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

	override fun afterTextChanged(s: Editable) = block(s.toString())
})
