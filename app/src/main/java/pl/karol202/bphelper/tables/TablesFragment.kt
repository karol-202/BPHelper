package pl.karol202.bphelper.tables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.transaction
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import pl.karol202.bphelper.components.ExtendedFragment
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.act
import pl.karol202.bphelper.members.MembersViewModel
import pl.karol202.bphelper.ui.TablesFragmentArgs

class TablesFragment : ExtendedFragment()
{
	private val arguments by navArgs<TablesFragmentArgs>()
	private val configurationType get() = TableConfigurationType.findByName(arguments.tableConfigurationType)

	private var tableConfiguration by instanceStateOr { createTableConfiguration() }

	private val membersViewModel by lazy { ViewModelProviders.of(act).get<MembersViewModel>() }
	private val members get() = membersViewModel.allMembers.value

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(getLayout(), container, false)

	private fun getLayout() = when(tableConfiguration)
	{
		is TableConfiguration4X2 -> R.layout.fragment_tables_4
		is TableConfiguration2X3,
		is TableConfiguration2X4 -> R.layout.fragment_tables_2
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		childFragmentManager.transaction {
			addFragmentsForTableConfiguration()
		}
	}

	private fun FragmentTransaction.addFragmentsForTableConfiguration()
	{
		val tableConfiguration = tableConfiguration
		when(tableConfiguration)
		{
			is TableConfiguration4X2 ->
			{
				add(
					R.id.frameTable1,
					TableFragment.create(tableConfiguration.openingGov)
				)
				add(
					R.id.frameTable2,
					TableFragment.create(tableConfiguration.openingOpp)
				)
				add(
					R.id.frameTable3,
					TableFragment.create(tableConfiguration.closingGov)
				)
				add(
					R.id.frameTable4,
					TableFragment.create(tableConfiguration.closingOpp)
				)
			}
			is TableConfiguration2X3 ->
			{
				add(
					R.id.frameTable1,
					TableFragment.create(tableConfiguration.gov)
				)
				add(
					R.id.frameTable2,
					TableFragment.create(tableConfiguration.opp)
				)
			}
			is TableConfiguration2X4 ->
			{
				add(
					R.id.frameTable1,
					TableFragment.create(tableConfiguration.gov)
				)
				add(
					R.id.frameTable2,
					TableFragment.create(tableConfiguration.opp)
				)
			}
		}
	}

	private fun createTableConfiguration(): TableConfiguration
	{
		val members = members ?: throw IllegalStateException("Members are null")
		return configurationType.createForMembers(members) ?: throw IllegalArgumentException("Members incompatible with table configuration")
	}
}
