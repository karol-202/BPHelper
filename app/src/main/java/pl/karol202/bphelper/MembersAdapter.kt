package pl.karol202.bphelper

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.sdk27.coroutines.onClick

class MembersAdapter(private val members: MutableList<Member>) : RecyclerView.Adapter<MembersAdapter.ViewHolder<Member>>()
{
	private inner class ViewHolderMemberUI : AnkoComponent<View>
	{
		override fun createView(ui: AnkoContext<View>) = with(ui) {
			lateinit var present: CheckBox
			lateinit var name: TextView
			lateinit var delete: ImageButton
			linearLayout {
				lparams(width = MATCH_PARENT, height = dip(56))
				orientation = LinearLayout.HORIZONTAL

				checkBox {
					present = this
				}.lparams {
					marginStart = dip(16)
				}

				textView {
					name = this
					textSize = 16f
				}.lparams(width = MATCH_PARENT) {
					marginStart = dip(32)
				}

				imageButton(R.drawable.ic_delete_white_24dp) {
					delete = this
				}.lparams(width = dip(24), height = dip(24)) {
					marginEnd = dip(16)
				}

				tag = ViewHolderMember(this, present, name, delete)
			}
		}
	}

	private inner class ViewHolderNoMemberUI : AnkoComponent<View>
	{
		override fun createView(ui: AnkoContext<View>) = with(ui) {
			linearLayout {
				lparams(width = MATCH_PARENT, height = dip(56)) {
					gravity = Gravity.CENTER_VERTICAL
				}
				orientation = LinearLayout.HORIZONTAL
				tag = ViewHolderNoMember(this)

				imageView(R.drawable.ic_add_white_24dp).lparams(width = dip(24), height = dip(24)) {
					marginStart = dip(16)
				}

				textView(R.string.text_no_member).lparams {
					marginStart = dip(32)
				}
			}
		}
	}

	abstract class ViewHolder<in M : Member?>(view: View) : RecyclerView.ViewHolder(view)
	{
		abstract fun bind(member: M)
	}

	inner class ViewHolderMember(private val view: View,
	                             private val present: CheckBox,
	                             private val name: TextView,
	                             private val delete: ImageButton) : ViewHolder<Member>(view)
	{
		override fun bind(member: Member)
		{
			view.onClick {
				present.isChecked = !present.isChecked
			}

			name.text = member.name

			present.isChecked = member.present
			present.onCheckedChange { _, checked ->
				member.present = checked
			}

			delete.onClick {
				with(view.ctx) {
					alert(Appcompat,
						  getString(R.string.alert_remove_member_title),
						  getString(R.string.alert_remove_member, member.name)) {
						positiveButton(R.string.action_remove) {
							removeMember(member)
						}
						negativeButton(R.string.action_cancel) { }
					}.show()
				}
			}
		}
	}

	inner class ViewHolderNoMember(private val view: View) : ViewHolder<Member?>(view)
	{
		override fun bind(member: Member?)
		{
			view.onClick {
				with(view.ctx) {
					alert(Appcompat, R.string.alert_add_member_title) {
						lateinit var name: EditText
						customView {
							editText {
								name = this
								setHint(R.string.text_hint_member_name)
							}
						}
						positiveButton(R.string.action_add) {
							addMember(name.text.toString())
						}
						negativeButton(R.string.action_cancel) { }
					}.show()
				}
			}
		}
	}

	private companion object
	{
		const val TYPE_MEMBER = 0
		const val TYPE_NO_MEMBER = 1
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Member> =
		if(viewType == TYPE_MEMBER) ViewHolderMemberUI().createView(AnkoContext.createFromView(parent)).tag as ViewHolderMember
		else ViewHolderNoMemberUI().createView(AnkoContext.createFromView(parent)).tag as ViewHolderNoMember

	override fun getItemCount() = members.size + 1

	override fun getItemViewType(position: Int) = if(position == members.size) TYPE_NO_MEMBER else TYPE_MEMBER

	override fun onBindViewHolder(holder: ViewHolder<Member>, position: Int)
	{
		if(holder is ViewHolderMember) holder.bind(members[position])
		else if(holder is ViewHolderNoMember) holder.bind(null)
	}

	private fun addMember(name: String)
	{
		members.add(Member(name, true))
		notifyItemInserted(members.size - 1)
	}

	private fun removeMember(member: Member)
	{
		members.remove(member)
		notifyItemRemoved(members.size)
	}
}
