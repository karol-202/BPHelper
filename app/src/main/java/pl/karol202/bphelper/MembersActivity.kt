package pl.karol202.bphelper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.setContentView

class MembersActivity : AppCompatActivity()
{
	private val members = listOf<Member>()

	private val membersAdapter = MembersAdapter(mutableListOf())

	private val ui = MembersActivityUI(membersAdapter)

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		MembersActivityUI(membersAdapter).setContentView(this)
	}
}

class MembersActivityUI(private val adapter: MembersAdapter) : AnkoComponent<MembersActivity>
{
	override fun createView(ui: AnkoContext<MembersActivity>) = with(ui) {
		coordinatorLayout {
			toolbar()
			recyclerView {
				layoutManager = LinearLayoutManager(ctx)
				adapter = this@MembersActivityUI.adapter
			}
		}
	}
}
