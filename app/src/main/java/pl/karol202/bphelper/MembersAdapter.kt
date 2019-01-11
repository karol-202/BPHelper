package pl.karol202.bphelper

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import kotlin.properties.Delegates

class MembersAdapter(private val members: List<Member>) : RecyclerView.Adapter<MembersAdapter.ViewHolder<Member>>()
{
	abstract class ViewHolder<in M : Member?>(view: View) : RecyclerView.ViewHolder(view)
	{
		abstract fun bind(member: M)
	}

	class ViewHolderMember(view: View,
	                       val name: TextView) : ViewHolder<Member>(view)
	{
		internal class UI : AnkoComponent<View>
		{
			override fun createView(ui: AnkoContext<View>) = with(ui) {
				var name by Delegates.notNull<TextView>()
				linearLayout {
					lparams(width = MATCH_PARENT, height = dip(48))
					orientation = LinearLayout.HORIZONTAL
					textView {
						name = this
					}.lparams {
						marginStart = 16
					}
					tag = ViewHolderMember(this, name)
				}
			}
		}

		override fun bind(member: Member)
		{
			name.text = member.name
		}
	}

	class ViewHolderNoMember(view: View) : ViewHolder<Member?>(view)
	{
		internal class UI : AnkoComponent<View>
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

		override fun bind(member: Member?) { }
	}

	private companion object
	{
		const val TYPE_MEMBER = 0
		const val TYPE_NO_MEMBER = 1
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Member> =
		if(viewType == TYPE_MEMBER) MembersAdapter.ViewHolderMember.UI().createView(AnkoContext.createFromView(parent)).tag as ViewHolderMember
		else ViewHolderNoMember.UI().createView(AnkoContext.createFromView(parent)).tag as ViewHolderNoMember

	override fun getItemCount() = members.size + 1

	override fun getItemViewType(position: Int) = if(position == members.size) TYPE_NO_MEMBER else TYPE_MEMBER

	override fun onBindViewHolder(holder: ViewHolder<Member>, position: Int)
	{
		if(holder is ViewHolderMember) holder.bind(members[position])
		else if(holder is ViewHolderNoMember) holder.bind(null)
	}
}
