package pl.karol202.bphelper.ui.tables

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_table_configuration.*
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.ctx

class TableConfigurationAdapter : BaseAdapter()
{
	class ViewHolder(override val containerView: View) : LayoutContainer
	{
		fun bind(tableConfigurationType: TableConfigurationType)
		{
			textTableConfigurationName.setText(tableConfigurationType.visibleName)
		}
	}

	override fun getItem(position: Int) = TableConfigurationType.values()[position]

	override fun getItemId(position: Int) = position.toLong()

	override fun getCount() = TableConfigurationType.values().size

	fun getPosition(item: TableConfigurationType) = TableConfigurationType.values().indexOf(item)

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val viewHolder = convertView?.tag as? ViewHolder
			?: createViewHolder(parent.ctx, parent)
		viewHolder.bind(getItem(position))
		return viewHolder.containerView
	}

	private fun createViewHolder(context: Context, parent: ViewGroup): ViewHolder
	{
		val view = LayoutInflater.from(context).inflate(R.layout.item_table_configuration, parent, false)
		return ViewHolder(view).also { view.tag = it }
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) = getView(position, convertView, parent)
}
