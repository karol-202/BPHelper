package pl.karol202.bphelper

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.Toolbar
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.google.android.material.chip.Chip
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design._CoordinatorLayout
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout

fun Context.startTablesActivity(configurationType: TableConfigurationType, members: List<Member>) =
	startActivity<TablesActivity>(TablesActivity::configurationType to configurationType,
	                              TablesActivity::members to members.toTypedArray())

class TablesActivity : BundledActivity()
{
	val configurationType by intentOrThrow<TableConfigurationType>()
	val members by intentOrThrow<Array<Parcelable>>().convert { it.map { parcelable -> parcelable as Member } }

	private var tableConfiguration by instanceState {
		createTableConfiguration()
	}

	private val ui by lazy { TablesActivityUI(tableConfiguration) }

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)

		ui.setContentView(this)
		initToolbar()
	}

	private fun initToolbar()
	{
		setSupportActionBar(ui.toolbar)
	}

	private fun createTableConfiguration() =
		configurationType.factory.createForMembers(members) ?: throw Exception("Members incompatible with table configuration")
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
				}.lparams(width = MATCH_PARENT)
			}.lparams(width = MATCH_PARENT)

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

	}

	private fun _CoordinatorLayout.createViewForTableConfiguration2x3(tableConfiguration: TableConfiguration2X3) = gridLayout {
		columnCount = 2

		createViewForTable3(tableConfiguration.gov)
		createViewForTable3(tableConfiguration.opp)
	}

	private fun _GridLayout.createViewForTable3(table: TableConfiguration2X3.Table) = verticalLayout {

	}
}