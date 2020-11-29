package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.domain.entity.TableConfiguration
import pl.karol202.bphelper.domain.entity.TableConfigurationError
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.domain.service.TableConfigurationService
import pl.karol202.bphelper.domain.util.Either
import pl.karol202.bphelper.interactors.usecases.table.DrawTableConfigurationUseCase
import pl.karol202.bphelper.presentation.util.Event
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationErrorViewData
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationViewData
import pl.karol202.bphelper.presentation.viewdata.toViewData
import pl.karol202.bphelper.presentation.viewmodel.TablesViewModel

class TablesViewModelImpl(private val drawTableConfigurationUseCase: DrawTableConfigurationUseCase) :
	BaseViewModel(), TablesViewModel
{
	private val _tableConfiguration = MutableStateFlow<TableConfiguration?>(null)
	private val _error = MutableStateFlow<Event<TableConfigurationError>?>(null)

	override val tableConfiguration = _tableConfiguration.mapNotNull { it?.toViewData() }
	override val error = _error.mapNotNull { it?.map(TableConfigurationError::toViewData) }

	override fun draw() = launch {
		drawTableConfigurationUseCase().let { result ->
			_tableConfiguration.value = result.rightOrNull()
			if(result is Either.Left) _error.value = Event(result.value)
		}
	}
}
