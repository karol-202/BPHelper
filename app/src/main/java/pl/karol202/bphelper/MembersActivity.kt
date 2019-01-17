package pl.karol202.bphelper

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick

class MembersActivity : AppCompatActivity()
{
	private lateinit var membersViewModel: MembersViewModel

	private val ui = MembersActivityUI()
	private val membersAdapter = MembersAdapter()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		initViewModel()

		ui.setContentView(this)
		initToolbar()
		initRecyclerView()
		initDrawButton()
	}

	private fun initViewModel()
	{
		membersViewModel = ViewModelProviders.of(this).get(MembersViewModel::class.java)
		membersViewModel.allMembers.observe(this, Observer { members ->
			members?.let { membersAdapter.members = members }
		})
	}

	private fun initToolbar()
	{
		setSupportActionBar(ui.toolbar)
	}

	private fun initRecyclerView()
	{
		membersAdapter.callback = object : MembersAdapter.Callback {
			override fun addMember(member: Member)
			{
				membersViewModel.addMember(member)
			}

			override fun updateMember(member: Member)
			{
				membersViewModel.updateMember(member)
			}

			override fun removeMember(member: Member)
			{
				membersViewModel.removeMember(member)
			}
		}

		ui.recyclerView.adapter = membersAdapter
	}

	private fun initDrawButton()
	{
		ui.drawButton.onClick {
			startActivity<TablesActivity>()
		}
	}
}

private class MembersActivityUI : AnkoComponent<MembersActivity>
{
	lateinit var toolbar: Toolbar
		private set
	lateinit var recyclerView: RecyclerView
		private set
	lateinit var drawButton: FloatingActionButton
	private set

	override fun createView(ui: AnkoContext<MembersActivity>) = with(ui) {
		coordinatorLayout {
			appBarLayout {
				toolbar {
					toolbar = this
					doOnApi(Build.VERSION_CODES.LOLLIPOP) { elevation = dip(4).toFloat() }
				}.lparams(width = MATCH_PARENT)
			}.lparams(width = MATCH_PARENT)

			recyclerView {
				recyclerView = this
				layoutManager = LinearLayoutManager(ctx)
				addItemDecoration(ItemDivider(ctx.getDrawableCompat(R.drawable.divider)))
			}.lparams(width = MATCH_PARENT) {
				behavior = AppBarLayout.ScrollingViewBehavior()
			}

			floatingActionButton {
				drawButton = this
				size = FloatingActionButton.SIZE_NORMAL
				imageResource = R.drawable.ic_random_white_24dp
				doOnApi(Build.VERSION_CODES.LOLLIPOP) { elevation = dip(6).toFloat() }
			}.lparams {
				gravity = Gravity.END or Gravity.BOTTOM
				margin = dip(16)
			}
		}
	}
}
