package pl.karol202.bphelper.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_member.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.sdk27.coroutines.onClick
import pl.karol202.bphelper.Member
import pl.karol202.bphelper.R

class MembersAdapter : RecyclerView.Adapter<MembersAdapter.ViewHolder<Member>>()
{
	abstract class ViewHolder<in M : Member?>(view: View) : RecyclerView.ViewHolder(view)
	{
		abstract fun bind(member: M)
	}

	inner class ViewHolderMember(override val containerView: View) : ViewHolder<Member>(containerView), LayoutContainer
	{
		override fun bind(member: Member)
		{
			containerView.onClick {
				checkMemberPresent.isChecked = !checkMemberPresent.isChecked
			}

			textMemberName.text = member.name

			checkMemberPresent.isChecked = member.present
			checkMemberPresent.onCheckedChange { _, checked ->
				member.present = checked
				callback?.updateMember(member)
				checkMemberIronman.visibility = if(checked) View.VISIBLE else View.GONE
			}

			buttonMemberDelete.onClick {
				with(containerView.ctx) {
					alertDialog {
						setTitle(getString(pl.karol202.bphelper.R.string.alert_remove_member_title))
						setMessage(getString(pl.karol202.bphelper.R.string.alert_remove_member, member.name))
						setPositiveButton(pl.karol202.bphelper.R.string.action_remove) { _, _ ->
							callback?.removeMember(member)
						}
						setNegativeButton(pl.karol202.bphelper.R.string.action_cancel, null)
					}.show()
				}
			}

			checkMemberIronman.visibility = if(member.present) View.VISIBLE else View.GONE
			checkMemberIronman.isChecked = member.ironman
			checkMemberIronman.onCheckedChange { _, checked ->
				member.ironman = checked
				callback?.updateMember(member)
			}
		}
	}

	inner class ViewHolderNoMember(override val containerView: View) : ViewHolder<Member?>(containerView), LayoutContainer
	{
		override fun bind(member: Member?)
		{
			containerView.onClick {
				with(containerView.ctx) {
					memberAddDialog {
						setOnAddListener { name ->
							callback?.addMember(Member(name, true))
						}
					}.show()
				}
			}
		}
	}

	interface Callback
	{
		fun addMember(member: Member)

		fun updateMember(member: Member)

		fun removeMember(member: Member)
	}

	private companion object
	{
		const val TYPE_MEMBER = 0
		const val TYPE_NO_MEMBER = 1
	}

	var callback: Callback? = null
	var members = emptyList<Member>()
		set(value)
		{
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Member>
	{
		val inflater = LayoutInflater.from(parent.ctx)
		return if(viewType == TYPE_MEMBER) ViewHolderMember(inflater.inflate(
			R.layout.item_member, parent, false))
		else ViewHolderNoMember(inflater.inflate(R.layout.item_no_member, parent, false))
	}

	override fun getItemCount() = members.size + 1

	override fun getItemViewType(position: Int) = if(position == members.size) TYPE_NO_MEMBER else TYPE_MEMBER

	override fun onBindViewHolder(holder: ViewHolder<Member>, position: Int)
	{
		if(holder is ViewHolderMember) holder.bind(members[position])
		else if(holder is ViewHolderNoMember) holder.bind(null)
	}
}
