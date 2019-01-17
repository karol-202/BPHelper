package pl.karol202.bphelper

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.setContentView

class TablesActivity : AppCompatActivity()
{
	private lateinit var membersViewModel: MembersViewModel

	private val ui = TablesActivityUI()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		initViewModel()

		ui.setContentView(this)
	}

	private fun initViewModel()
	{
		membersViewModel = ViewModelProviders.of(this).get(MembersViewModel::class.java)
	}
}

private class TablesActivityUI : AnkoComponent<TablesActivity>
{
	override fun createView(ui: AnkoContext<TablesActivity>) = with(ui) {
		linearLayout()
	}
}