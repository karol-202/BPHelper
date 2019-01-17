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

class MembersAdapter : RecyclerView.Adapter<MembersAdapter.ViewHolder<Member>>()
{
	private inner class ViewHolderMemberUI : AnkoComponent<View>
	{
		override fun createView(ui: AnkoContext<View>) = with(ui) {
			lateinit var present: CheckBox
			lateinit var name: TextView
			lateinit var delete: ImageButton
			lateinit var ironman: CheckBox
			verticalLayout {
				lparams(width = MATCH_PARENT)
				backgroundResource = ctx.attr(R.attr.selectableItemBackground).resourceId

				linearLayout {
					orientation = LinearLayout.HORIZONTAL
					gravity = Gravity.CENTER_VERTICAL

					checkBox {
						present = this
					}.lparams {
						marginStart = dip(16)
					}

					textView {
						name = this
						textSize = 16f
						textColorResource = R.color.text_member
					}.lparams {
						marginStart = dip(24)
					}

					space().lparams {
						weight = 1f
					}

					imageButton(R.drawable.ic_delete_white_24dp) {
						delete = this
						backgroundResource = ctx.attr(R.attr.selectableItemBackgroundBorderless).resourceId
					}.lparams(width = dip(24), height = dip(24)) {
						marginEnd = dip(16)
					}
				}.lparams(width = MATCH_PARENT, height = dip(56))

				checkBox(R.string.text_ironman) {
					ironman = this
					visibility = View.GONE
					textSize = 16f
				}.lparams {
					marginStart = dip(24)
					bottomMargin = dip(8)
				}

				tag = ViewHolderMember(this, present, name, delete, ironman)
			}
		}
	}

	private inner class ViewHolderNoMemberUI : AnkoComponent<View>
	{
		override fun createView(ui: AnkoContext<View>) = with(ui) {
			linearLayout {
				lparams(width = MATCH_PARENT, height = dip(56))
				orientation = LinearLayout.HORIZONTAL
				gravity = Gravity.CENTER_VERTICAL
				backgroundResource = ctx.attr(R.attr.selectableItemBackground).resourceId
				tag = ViewHolderNoMember(this)

				imageView(R.drawable.ic_add_white_24dp).lparams(width = dip(24), height = dip(24)) {
					marginStart = dip(16)
				}

				textView(R.string.text_no_member) {
					textSize = 16f
					textColorResource = R.color.text_member
				}.lparams {
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
	                             private val delete: ImageButton,
	                             private val ironman: CheckBox) : ViewHolder<Member>(view)
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
				callback?.updateMember(member)
				ironman.visibility = if(checked) View.VISIBLE else View.GONE
			}

			delete.onClick {
				with(view.ctx) {
					alert(Appcompat,
						  getString(R.string.alert_remove_member, member.name),
						  getString(R.string.alert_remove_member_title)) {
						positiveButton(R.string.action_remove) {
							callback?.removeMember(member)
						}
						negativeButton(R.string.action_cancel) { }
					}.show()
				}
			}

			ironman.visibility = if(member.present) View.VISIBLE else View.GONE
			ironman.isChecked = member.ironman
			ironman.onCheckedChange { _, checked ->
				member.ironman = checked
				callback?.updateMember(member)
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
							frameLayout {
								editText {
									name = this
									setHint(R.string.text_hint_member_name)
								}.lparams(width = MATCH_PARENT) {
									horizontalMargin = dip(24)
								}
							}
						}
						positiveButton(R.string.action_add) {
							callback?.addMember(Member(name.text.toString(), true))
						}
						negativeButton(R.string.action_cancel) { }
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
}
