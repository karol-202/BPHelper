package pl.karol202.bphelper.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_member.*
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.ui.R
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

	private inner class MemberViewHolder(override val containerView: View) : ViewHolder<MemberViewData>(containerView),
	                                                                         LayoutContainer
	{
		private val checkMemberPresent = check_member_present as CheckBox
		private val checkMemberIronman = check_member_ironman as CheckBox

		init
		{
			containerView.setOnClickListener {
				checkMemberPresent.isChecked = !checkMemberPresent.isChecked
			}
		}

		override fun bind(member: MemberViewData)
		{
			text_member_name.text = member.name

			checkMemberPresent.setOnCheckedChangeListener(null)
			checkMemberPresent.isChecked = member.present
			checkMemberPresent.setOnCheckedChangeListener { _, checked ->
				onMemberUpdate(member.copy(present = checked))
			}

			checkMemberIronman.setOnCheckedChangeListener(null)
			checkMemberIronman.visibility = if(member.present) View.VISIBLE else View.GONE
			checkMemberIronman.isChecked = member.ironman
			checkMemberIronman.setOnCheckedChangeListener { _, checked ->
				onMemberUpdate(member.copy(ironman = checked))
			}

			button_member_delete.setOnClickListener {
				onMemberRemove(member)
			}
		}
	}

	private inner class ViewHolderNoMember(override val containerView: View) : ViewHolder<MemberViewData?>(containerView),
	                                                                           LayoutContainer
	{
		init
		{
			containerView.setOnClickListener { onMemberAdd() }
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
		fun inflate(@LayoutRes layout: Int) = inflater.inflate(layout, parent, false)

		return if(viewType == TYPE_MEMBER) MemberViewHolder(inflate(R.layout.item_member))
		else ViewHolderNoMember(inflate(R.layout.item_no_member))
	}

	override fun getItemCount() = members.size + 1

	override fun getItemViewType(position: Int) = if(position == members.size) TYPE_NO_MEMBER else TYPE_MEMBER

	override fun onBindViewHolder(holder: ViewHolder<MemberViewData>, position: Int)
	{
		if(holder is MemberViewHolder) holder.bind(members[position])
		else if(holder is ViewHolderNoMember) holder.bind(null)
	}
}
