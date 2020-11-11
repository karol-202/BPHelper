package pl.karol202.bphelper.ui.tables

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_table_member.*
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.ctx
import pl.karol202.bphelper.ui.members.MemberEntity

class TableMembersAdapter(private val members: List<MemberEntity>) : RecyclerView.Adapter<TableMembersAdapter.ViewHolder>()
{
	class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
	{
		fun bind(member: MemberEntity)
		{
			chipTableMember.text = member.name
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_table_member, parent, false)
		return ViewHolder(view)
	}

	override fun getItemCount() = members.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		val member = members[position]
		holder.bind(member)
	}
}
