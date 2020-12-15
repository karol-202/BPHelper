package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationErrorViewData
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationViewData

interface TablesViewModel : ViewModel
{
	val tableConfiguration: Flow<TableConfigurationViewData?>
	val error: Flow<TableConfigurationErrorViewData>

	fun draw()
}
