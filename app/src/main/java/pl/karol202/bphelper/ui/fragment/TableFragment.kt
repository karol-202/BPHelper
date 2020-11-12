package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_table.*
import pl.karol202.bphelper.components.ExtendedFragment
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.setArguments
import pl.karol202.bphelper.extensions.to
import pl.karol202.bphelper.ui.adapter.TableMembersAdapter
import pl.karol202.bphelper.TableConfiguration

class TableFragment : ExtendedFragment()
{
	companion object
	{
		fun create(table: TableConfiguration.Table) =
			TableFragment().setArguments(TableFragment::table to table)
	}

	private val table by argumentsOrThrow<TableConfiguration.Table>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_table, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		textTableName.text = getString(table.name)

		recyclerTable.layoutManager = LinearLayoutManager(ctx)
		recyclerTable.adapter = TableMembersAdapter(table.allMembers)
	}
}
