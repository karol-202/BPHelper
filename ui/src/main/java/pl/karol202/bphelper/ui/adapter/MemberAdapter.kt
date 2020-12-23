package pl.karol202.bphelper.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.ui.databinding.ItemMemberBinding
import pl.karol202.bphelper.ui.databinding.ItemNoMemberBinding
import pl.karol202.bphelper.ui.extensions.ctx

class MemberAdapter(private val onMemberAdd: () -> Unit,
                    private val onMemberUpdate: (MemberViewData) -> Unit,
                    private val onMemberRemove: (MemberViewData) -> Unit) :
	RecyclerView.Adapter<MemberAdapter.ViewHolder<MemberViewData>>()
{
	abstract class ViewHolder<in M : MemberViewData?>(view: View) : RecyclerView.ViewHolder(view)
	{
		abstract fun bind(member: M)
	}

	private inner class MemberViewHolder(private val views: ItemMemberBinding) : ViewHolder<MemberViewData>(views.root)
	{
		init
		{
			views.root.setOnClickListener {
				views.checkMemberPresent.isChecked = !views.checkMemberPresent.isChecked
			}
		}

		override fun bind(member: MemberViewData)
		{
			views.textMemberName.text = member.name

			views.checkMemberPresent.setOnCheckedChangeListener(null)
			views.checkMemberPresent.isChecked = member.present
			views.checkMemberPresent.setOnCheckedChangeListener { _, checked ->
				onMemberUpdate(member.copy(present = checked))
			}

			views.checkMemberIronman.setOnCheckedChangeListener(null)
			views.checkMemberIronman.visibility = if(member.present) View.VISIBLE else View.GONE
			views.checkMemberIronman.isChecked = member.ironman
			views.checkMemberIronman.setOnCheckedChangeListener { _, checked ->
				onMemberUpdate(member.copy(ironman = checked))
			}

			views.buttonMemberDelete.setOnClickListener {
				onMemberRemove(member)
			}
		}
	}

	private inner class ViewHolderNoMember(views: ItemNoMemberBinding) : ViewHolder<MemberViewData?>(views.root)
	{
		init
		{
			views.root.setOnClickListener { onMemberAdd() }
		}

		override fun bind(member: MemberViewData?) { }
	}

	private class DiffCallback(private val oldList: List<MemberViewData>,
	                           private val newList: List<MemberViewData>) : DiffUtil.Callback()
	{
		override fun getOldListSize() = oldList.size

		override fun getNewListSize() = newList.size

		override fun areItemsTheSame(oldIndex: Int, newIndex: Int) = oldList[oldIndex].id == newList[newIndex].id

		override fun areContentsTheSame(oldIndex: Int, newIndex: Int) = oldList[oldIndex] == newList[newIndex]
	}

	private companion object
	{
		const val TYPE_MEMBER = 0
		const val TYPE_NO_MEMBER = 1
	}

	var members = emptyList<MemberViewData>()
		set(value)
		{
			val result = DiffUtil.calculateDiff(DiffCallback(field, value))
			field = value
			result.dispatchUpdatesTo(this)
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<MemberViewData>
	{
		val inflater = LayoutInflater.from(parent.ctx)
		return if(viewType == TYPE_MEMBER) MemberViewHolder(ItemMemberBinding.inflate(inflater, parent, false))
		else ViewHolderNoMember(ItemNoMemberBinding.inflate(inflater, parent, false))
	}

	override fun getItemCount() = members.size + 1

	override fun getItemViewType(position: Int) = if(position == members.size) TYPE_NO_MEMBER else TYPE_MEMBER

	override fun onBindViewHolder(holder: ViewHolder<MemberViewData>, position: Int)
	{
		if(holder is MemberViewHolder) holder.bind(members[position])
		else if(holder is ViewHolderNoMember) holder.bind(null)
	}
}
