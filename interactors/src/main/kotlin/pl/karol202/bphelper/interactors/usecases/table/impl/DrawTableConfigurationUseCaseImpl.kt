package pl.karol202.bphelper.interactors.usecases.table.impl

import pl.karol202.bphelper.domain.service.TableConfigurationService
import pl.karol202.bphelper.interactors.usecases.member.GetMembersUseCase
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase
import pl.karol202.bphelper.interactors.usecases.table.DrawTableConfigurationUseCase

class DrawTableConfigurationUseCaseImpl(private val tableConfigurationService: TableConfigurationService,
                                        private val getMembersUseCase: GetMembersUseCase,
                                        private val getSettingsUseCase: GetSettingsUseCase) : DrawTableConfigurationUseCase
{
	override suspend fun invoke() = tableConfigurationService.createConfiguration(
		tableConfigurationType = getSettingsUseCase().tableConfigurationType,
		members = getMembersUseCase()
	)
}
