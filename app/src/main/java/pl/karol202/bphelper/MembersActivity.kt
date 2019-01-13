package pl.karol202.bphelper

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.setContentView
import kotlin.properties.Delegates

class MembersActivity : AppCompatActivity()
{
	private lateinit var membersViewModel: MembersViewModel

	private val ui = MembersActivityUI()
	private val membersAdapter = MembersAdapter()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		initViewModel()

		ui.setContentView(this)
		initToolbar()
		initRecyclerView()
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

			override fun removeMember(member: Member)
			{
				membersViewModel.removeMember(member)
			}
		}

		ui.recyclerView.adapter = membersAdapter
	}
}

class MembersActivityUI : AnkoComponent<MembersActivity>
{
	var toolbar by Delegates.notNull<Toolbar>()
		private set
	var recyclerView by Delegates.notNull<RecyclerView>()
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
			}.lparams(width = MATCH_PARENT) {
				behavior = AppBarLayout.ScrollingViewBehavior()
			}
		}
	}
}
