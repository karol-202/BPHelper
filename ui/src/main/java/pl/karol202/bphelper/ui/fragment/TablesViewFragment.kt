package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationViewData
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedFragment
import pl.karol202.bphelper.ui.extensions.setArguments
import pl.karol202.bphelper.ui.extensions.to

class TablesViewFragment : ExtendedFragment()
{
	companion object
	{
		fun create(configuration: TableConfigurationViewData) =
			TablesViewFragment().setArguments(TablesViewFragment::tableConfiguration to configuration)
	}

	private val tableConfiguration by argumentsOrThrow<TableConfigurationViewData>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(getLayout(), container, false)

	private fun getLayout() = when(tableConfiguration)
	{
		is TableConfigurationViewData.TwoTables -> R.layout.fragment_tables_2
		is TableConfigurationViewData.FourTables -> R.layout.fragment_tables_4
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		childFragmentManager.commit { addFragmentsForTableConfiguration() }
	}

	private fun FragmentTransaction.addFragmentsForTableConfiguration() = when(val configuration = tableConfiguration)
	{
		is TableConfigurationViewData.TwoTables ->
		{
			add(R.id.frame_table_1, TableViewFragment.create(configuration.first))
			add(R.id.frame_table_2, TableViewFragment.create(configuration.second))
		}
		is TableConfigurationViewData.FourTables ->
		{
			add(R.id.frame_table_1, TableViewFragment.create(configuration.first))
			add(R.id.frame_table_2, TableViewFragment.create(configuration.second))
			add(R.id.frame_table_3, TableViewFragment.create(configuration.third))
			add(R.id.frame_table_4, TableViewFragment.create(configuration.fourth))
		}
	}
}
