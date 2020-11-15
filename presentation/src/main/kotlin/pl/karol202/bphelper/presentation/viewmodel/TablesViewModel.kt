package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.model.TableConfiguration
import pl.karol202.bphelper.domain.model.TableConfigurationError
import pl.karol202.bphelper.presentation.util.Event

interface TablesViewModel : ViewModel
{
	val tableConfiguration: Flow<TableConfiguration?>
	val error: Flow<Event<TableConfigurationError>>
}
