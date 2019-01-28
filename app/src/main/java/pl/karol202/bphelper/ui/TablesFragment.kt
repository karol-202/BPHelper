package pl.karol202.bphelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import pl.karol202.bphelper.MembersViewModel
import pl.karol202.bphelper.R
import pl.karol202.bphelper.TableConfigurationType
import pl.karol202.bphelper.ui.extensions.act

class TablesFragment : BundledFragment()
{
	private val arguments by navArgs<TablesFragmentArgs>()
	private val configurationType get() = TableConfigurationType.findByName(arguments.tableConfigurationType)
	private var tableConfiguration by instanceStateOr { createTableConfiguration() }

	private val membersViewModel by lazy { ViewModelProviders.of(act).get<MembersViewModel>() }
	private val members get() = membersViewModel.allMembers.value

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_tables, container, false)

	private fun createTableConfiguration()
	{
		val members = members ?: throw IllegalStateException("Members are null")
		configurationType.createForMembers(members) ?: throw IllegalArgumentException("Members incompatible with table configuration")
	}
}