package pl.karol202.bphelper.extensions

import android.content.Context
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView

val View.ctx: Context get() = context

fun <T : Adapter> AdapterView<T>.setOnItemSelectedListener(
	listener: ((parent: AdapterView<*>?, view: View?, position: Int, id: Long) -> Unit)?
)
{
	onItemSelectedListener = listener?.let {
		object : AdapterView.OnItemSelectedListener
		{
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
				it(parent, view, position, id)

			override fun onNothingSelected(parent: AdapterView<*>?) { }
		}
	}
}