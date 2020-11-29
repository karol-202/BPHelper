package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.TableConfiguration
import pl.karol202.bphelper.domain.entity.TableConfigurationError
import pl.karol202.bphelper.presentation.util.Event
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationErrorViewData
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationViewData

interface TablesViewModel : ViewModel
{
	val tableConfiguration: Flow<TableConfigurationViewData>
	val error: Flow<Event<TableConfigurationErrorViewData>>

	fun draw()
}
