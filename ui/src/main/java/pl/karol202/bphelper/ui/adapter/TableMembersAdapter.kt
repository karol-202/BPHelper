package pl.karol202.bphelper.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_table_member.*
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.extensions.ctx

class TableMembersAdapter(private val members: List<MemberViewData>) : RecyclerView.Adapter<TableMembersAdapter.ViewHolder>()
{
	class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
	{
		fun bind(member: MemberViewData)
		{
			chip_table_member.text = member.name
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ViewHolder(LayoutInflater.from(parent.ctx).inflate(R.layout.item_table_member, parent, false))

	override fun getItemCount() = members.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(members[position])
}
