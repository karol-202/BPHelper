package pl.karol202.bphelper.ui

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.synthetic.main.activity_tables.*
import pl.karol202.bphelper.*

fun Context.startTablesActivity(configurationType: TableConfigurationType, members: List<Member>) =
	startActivity<TablesActivity>(
		TablesActivity::configurationType to configurationType,
		TablesActivity::members to members.toTypedArray())

class TablesActivity : BundledActivity()
{
	val configurationType by intentOrThrow<TableConfigurationType>()
	val members by intentOrThrow<Array<Parcelable>>().convert { it.map { parcelable -> parcelable as Member } }

	private var tableConfiguration by instanceState {
		createTableConfiguration()
	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_tables)

		initToolbar()
	}

	private fun initToolbar()
	{
		setSupportActionBar(toolbar)
	}

	private fun createTableConfiguration() =
		configurationType.factory.createForMembers(members) ?: throw Exception("Members incompatible with table configuration")
}
