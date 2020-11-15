package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.domain.model.TableConfiguration
import pl.karol202.bphelper.domain.model.TableConfigurationError
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.domain.service.TableConfigurationService
import pl.karol202.bphelper.domain.util.Either
import pl.karol202.bphelper.interactors.usecases.table.DrawTableConfigurationUseCase
import pl.karol202.bphelper.presentation.util.Event
import pl.karol202.bphelper.presentation.viewmodel.TablesViewModel

class TablesViewModelImpl(private val drawTableConfigurationUseCase: DrawTableConfigurationUseCase) :
	BaseViewModel(), TablesViewModel
{
	private val _tableConfiguration = MutableStateFlow<TableConfiguration?>(null)
	private val _error = MutableStateFlow<Event<TableConfigurationError>?>(null)

	override val tableConfiguration: Flow<TableConfiguration?> = _tableConfiguration
	override val error: Flow<Event<TableConfigurationError>> = _error.filterNotNull()

	fun draw() = launch {
		drawTableConfigurationUseCase().let { result ->
			_tableConfiguration.value = result.rightOrNull()
			if(result is Either.Left) _error.value = Event(result.value)
		}
	}
}
