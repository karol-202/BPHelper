package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_table.*
import pl.karol202.bphelper.presentation.viewdata.TableViewData
import pl.karol202.bphelper.ui.components.ExtendedFragment
import pl.karol202.bphelper.ui.adapter.TableMembersAdapter
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.extensions.ctx
import pl.karol202.bphelper.ui.extensions.setArguments
import pl.karol202.bphelper.ui.extensions.to

class TableViewFragment : ExtendedFragment()
{
	companion object
	{
		fun create(table: TableViewData) =
			TableViewFragment().setArguments(TableViewFragment::table to table)
	}

	private val table by argumentsOrThrow<TableViewData>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_table, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		text_table_name.text = getString(table.roleName)

		recycler_table.layoutManager = LinearLayoutManager(ctx)
		recycler_table.adapter = TableMembersAdapter(table.members)
	}

	private val TableViewData.roleName get() = when(role)
	{
		TableViewData.Role.GOV -> R.string.table_name_gov
		TableViewData.Role.OPP -> R.string.table_name_opp
		TableViewData.Role.OG -> R.string.table_name_og
		TableViewData.Role.OO -> R.string.table_name_oo
		TableViewData.Role.CG -> R.string.table_name_cg
		TableViewData.Role.CO -> R.string.table_name_co
	}
}
