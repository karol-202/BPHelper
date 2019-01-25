package pl.karol202.bphelper

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.jetbrains.anko.dip
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView

class TableConfigurationAdapter : BaseAdapter()
{
	override fun getItem(position: Int) = TableConfigurationType.values()[position]

	override fun getItemId(position: Int) = position.toLong()

	override fun getCount() = TableConfigurationType.values().size

	override fun getView(position: Int, convertView: View?, parent: ViewGroup) = with(parent.ctx) {
		val item = getItem(position)

		linearLayout {
			lparams(height = dip(48))
			gravity = Gravity.CENTER_VERTICAL

			textView(item.visibleName) {
				textSize = 16f
			}.lparams {
				marginStart = dip(16)
				marginEnd = dip(32)
			}
		}
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) = getView(position, convertView, parent)
}