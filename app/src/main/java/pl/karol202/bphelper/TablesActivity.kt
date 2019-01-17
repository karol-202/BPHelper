package pl.karol202.bphelper

import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.setContentView

class TablesActivity : AppCompatActivity()
{
	companion object
	{
		const val KEY_TABLE_CONFIGURATION = "key_table_configuration"
	}

	private lateinit var membersViewModel: MembersViewModel
	private lateinit var tableConfiguration: TableConfiguration

	private val ui = TablesActivityUI()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		initViewModel()
		initTablesConfiguration(savedInstanceState)

		ui.setContentView(this)
		initToolbar()
	}

	private fun initViewModel()
	{
		membersViewModel = ViewModelProviders.of(this).get(MembersViewModel::class.java)
	}

	private fun initTablesConfiguration(savedInstanceState: Bundle?)
	{
		val savedConfiguration = savedInstanceState?.getParcelable<TableConfiguration>("tableConfiguration")
		tableConfiguration = savedConfiguration ?: createTableConfiguration() ?: throw Exception("Cannot create configuration")
	}

	private fun createTableConfiguration(): TableConfiguration?
	{
		val configurationType = TableConfigurationType.valueOf(intent.getStringExtra(KEY_TABLE_CONFIGURATION))
		return configurationType.factory.createForMembers(membersViewModel.allMembers.value ?: return null)
	}

	private fun initToolbar()
	{
		setSupportActionBar(ui.toolbar)
	}
}

private class TablesActivityUI : AnkoComponent<TablesActivity>
{
	lateinit var toolbar: Toolbar

	override fun createView(ui: AnkoContext<TablesActivity>) = with(ui) {
		coordinatorLayout {
			appBarLayout {
				toolbar {
					toolbar = this
					doOnApi(Build.VERSION_CODES.LOLLIPOP) { elevation = dip(4).toFloat() }
				}
			}

		}
	}
}