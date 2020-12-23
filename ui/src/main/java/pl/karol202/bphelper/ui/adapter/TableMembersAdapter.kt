package pl.karol202.bphelper.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.ui.databinding.ItemTableMemberBinding
import pl.karol202.bphelper.ui.extensions.ctx

class TableMembersAdapter(private val members: List<MemberViewData>) : RecyclerView.Adapter<TableMembersAdapter.ViewHolder>()
{
	class ViewHolder(private val views: ItemTableMemberBinding) : RecyclerView.ViewHolder(views.root)
	{
		fun bind(member: MemberViewData)
		{
			views.chipTableMember.text = member.name
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ViewHolder(ItemTableMemberBinding.inflate(LayoutInflater.from(parent.ctx), parent, false))

	override fun getItemCount() = members.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(members[position])
}
