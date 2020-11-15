package pl.karol202.bphelper.interactors.usecases.table.impl

import pl.karol202.bphelper.domain.service.TableConfigurationService
import pl.karol202.bphelper.interactors.usecases.member.GetMembersUseCase
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase
import pl.karol202.bphelper.interactors.usecases.table.CheckIfTableConfigurationPossibleUseCase
import pl.karol202.bphelper.interactors.usecases.table.DrawTableConfigurationUseCase

class CheckIfTableConfigurationPossibleUseCaseImpl(private val tableConfigurationService: TableConfigurationService,
                                                   private val getMembersUseCase: GetMembersUseCase,
                                                   private val getSettingsUseCase: GetSettingsUseCase) :
	CheckIfTableConfigurationPossibleUseCase
{
	override suspend fun invoke() = tableConfigurationService.isConfigurationPossible(
		tableConfigurationType = getSettingsUseCase().tableConfigurationType,
		members = getMembersUseCase()
	)
}
