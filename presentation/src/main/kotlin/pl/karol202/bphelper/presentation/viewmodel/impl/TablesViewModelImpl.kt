package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.domain.model.TableConfiguration
import pl.karol202.bphelper.domain.model.TableConfigurationError
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.domain.service.TableConfigurationService
import pl.karol202.bphelper.domain.util.Either
import pl.karol202.bphelper.presentation.viewmodel.TablesViewModel

class TablesViewModelImpl(private val tableConfigurationService: TableConfigurationService,
                          private val membersRepository: MemberRepository,
                          private val settingsRepository: SettingsRepository) : BaseViewModel(), TablesViewModel
{
	private val tableConfigurationResult = MutableStateFlow<Either<TableConfigurationError, TableConfiguration>?>(null)

	override val tableConfiguration = tableConfigurationResult.map { it?.rightOrNull() }
	override val error = tableConfigurationResult.map { it?.leftOrNull() }

	fun draw() = launch {
		tableConfigurationResult.value =
			tableConfigurationService.createConfiguration(settingsRepository.settings.tableConfigurationType,
			                                              membersRepository.allMembers.first())
	}
}
