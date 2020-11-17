package pl.karol202.bphelper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.karol202.bphelper.presentation.util.collectIn
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationViewData
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.components.ExtendedFragment
import pl.karol202.bphelper.ui.viewmodel.AndroidTablesViewModel

class TablesFragment : ExtendedFragment()
{
	private val navController by lazy { NavHostFragment.findNavController(this) }

	private val tablesViewModel by viewModel<AndroidTablesViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_tables_container, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		observeTableConfiguration()
		observeError()

		tablesViewModel.draw()
	}

	private fun observeTableConfiguration() = tablesViewModel.tableConfiguration.collectIn(lifecycleScope) { configuration ->
		replaceContent(configuration)
	}

	private fun observeError() = tablesViewModel.error.collectIn(lifecycleScope) { error ->
		navController.popBackStack()
	}

	private fun replaceContent(configuration: TableConfigurationViewData) = childFragmentManager.commit {
		replace(R.id.frame_tables_container, TablesViewFragment.create(configuration))
	}
}
