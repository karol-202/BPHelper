package pl.karol202.bphelper

import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
	private val members = mutableListOf(Member("Person 1"),
								 		Member("Member 2", true))

	private val membersAdapter = MembersAdapter(members)

	private val ui = MembersActivityUI(membersAdapter)

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		ui.setContentView(this)
		initToolbar()
	}

	private fun initToolbar()
	{
		setSupportActionBar(ui.toolbar)
	}
}

class MembersActivityUI(private val adapter: MembersAdapter) : AnkoComponent<MembersActivity>
{
	var toolbar by Delegates.notNull<Toolbar>()
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
				layoutManager = LinearLayoutManager(ctx)
				adapter = this@MembersActivityUI.adapter
			}.lparams(width = MATCH_PARENT) {
				behavior = AppBarLayout.ScrollingViewBehavior()
			}
		}
	}
}
