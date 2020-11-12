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
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.alertDialog
import pl.karol202.bphelper.extensions.ctx
import pl.karol202.bphelper.domain.model.Member

class MemberAdapter(private val memberAddListener: () -> Unit,
                    private val memberUpdateListener: (Member) -> Unit,
                    private val memberRemoveListener: (Member) -> Unit) :
	RecyclerView.Adapter<MemberAdapter.ViewHolder<Member>>()
{
	abstract class ViewHolder<in M : Member?>(view: View) : RecyclerView.ViewHolder(view)
	{
		abstract fun bind(member: M)
	}

	private inner class MemberViewHolder(override val containerView: View) : ViewHolder<Member>(containerView),
	                                                                         LayoutContainer
	{
		private val _checkMemberPresent = checkMemberPresent as CheckBox
		private val _checkMemberIronman = checkMemberIronman as CheckBox

		private var member: Member? = null

		init
		{
			containerView.setOnClickListener {
				_checkMemberPresent.isChecked = !_checkMemberPresent.isChecked
			}

			_checkMemberPresent.setOnCheckedChangeListener { _, checked ->
				val member = member ?: return@setOnCheckedChangeListener
				member.present = checked
				if(!checked) member.ironman = false

				memberUpdateListener(member)
				_checkMemberIronman.isChecked = member.ironman
				_checkMemberIronman.visibility = if(checked) View.VISIBLE else View.GONE
			}

			_checkMemberIronman.setOnCheckedChangeListener { _, checked ->
				val member = member ?: return@setOnCheckedChangeListener
				member.ironman = checked
				memberUpdateListener(member)
			}

			buttonMemberDelete.setOnClickListener {
				val member = member ?: return@setOnClickListener
				showMemberDeleteDialog(member)
			}
		}

		private fun showMemberDeleteDialog(member: Member) = with(containerView.ctx) {
			alertDialog {
				setTitle(getString(R.string.alert_remove_member_title))
				setMessage(getString(R.string.alert_remove_member, member.name))
				setPositiveButton(R.string.action_remove) { _, _ ->
					memberRemoveListener(member)
				}
				setNegativeButton(R.string.action_cancel, null)
			}.show()
		}

		override fun bind(member: Member)
		{
			this.member = null

			textMemberName.text = member.name

			_checkMemberPresent.isChecked = member.present

			_checkMemberIronman.visibility = if(member.present) View.VISIBLE else View.GONE
			_checkMemberIronman.isChecked = member.ironman

			this.member = member
		}
	}

	private inner class ViewHolderNoMember(override val containerView: View) : ViewHolder<Member?>(containerView), LayoutContainer
	{
		init
		{
			containerView.setOnClickListener { memberAddListener() }
		}

		override fun bind(member: Member?) { }
	}

	private class DiffCallback(private val oldList: List<Member>,
	                           private val newList: List<Member>) : DiffUtil.Callback()
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

	var members = emptyList<Member>()
		set(value)
		{
			val result = DiffUtil.calculateDiff(DiffCallback(field, value))
			field = value
			result.dispatchUpdatesTo(this)
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Member>
	{
		val inflater = LayoutInflater.from(parent.ctx)
		fun inflate(@LayoutRes layout: Int) = inflater.inflate(layout, parent, false)

		return if(viewType == TYPE_MEMBER) MemberViewHolder(inflate(R.layout.item_member))
		else ViewHolderNoMember(inflate(R.layout.item_no_member))
	}

	override fun getItemCount() = members.size + 1

	override fun getItemViewType(position: Int) = if(position == members.size) TYPE_NO_MEMBER else TYPE_MEMBER

	override fun onBindViewHolder(holder: ViewHolder<Member>, position: Int)
	{
		if(holder is MemberViewHolder) holder.bind(members[position])
		else if(holder is ViewHolderNoMember) holder.bind(null)
	}
}
