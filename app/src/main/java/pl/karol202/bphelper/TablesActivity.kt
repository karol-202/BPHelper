package pl.karol202.bphelper

import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.google.android.material.chip.Chip
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design._CoordinatorLayout
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout

class TablesActivity : AppCompatActivity()
{
	companion object
	{
		const val KEY_TABLE_CONFIGURATION = "key_table_configuration"
	}

	private lateinit var membersViewModel: MembersViewModel
	private lateinit var tableConfiguration: TableConfiguration

	private lateinit var ui: TablesActivityUI

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		initViewModel()
		initTablesConfiguration(savedInstanceState)
		initUI()
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

	private fun initUI()
	{
		ui = TablesActivityUI(tableConfiguration)
		ui.setContentView(this)
		initToolbar()
	}

	private fun initToolbar()
	{
		setSupportActionBar(ui.toolbar)
	}
}

private class TablesActivityUI(private val tableConfiguration: TableConfiguration) : AnkoComponent<TablesActivity>
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
			createViewForTableConfiguration()
		}
	}

	private fun _CoordinatorLayout.createViewForTableConfiguration() = when(tableConfiguration)
	{
		is TableConfiguration4X2 -> createViewForTableConfiguration4x2(tableConfiguration)
		is TableConfiguration2X3 -> createViewForTableConfiguration2x3(tableConfiguration)
		else -> throw Exception("Unsupported table configuration")
	}

	private fun _CoordinatorLayout.createViewForTableConfiguration4x2(tableConfiguration: TableConfiguration4X2) = gridLayout {
		columnCount = 2

		createViewForTable2(tableConfiguration.openingGov)
		createViewForTable2(tableConfiguration.openingOpp)
		createViewForTable2(tableConfiguration.closingGov)
		createViewForTable2(tableConfiguration.closingOpp)
	}

	private fun _GridLayout.createViewForTable2(table: TableConfiguration4X2.Table) = verticalLayout {
		chip
	}

	private fun _CoordinatorLayout.createViewForTableConfiguration2x3(tableConfiguration: TableConfiguration2X3) = gridLayout {
		columnCount = 2

		createViewForTable3(tableConfiguration.gov)
		createViewForTable3(tableConfiguration.opp)
	}

	private fun _GridLayout.createViewForTable3(table: TableConfiguration2X3.Table) = verticalLayout {

	}
}