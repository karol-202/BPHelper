package pl.karol202.bphelper

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.Toolbar
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.setContentView

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

	private val ui = TablesActivityUI()

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