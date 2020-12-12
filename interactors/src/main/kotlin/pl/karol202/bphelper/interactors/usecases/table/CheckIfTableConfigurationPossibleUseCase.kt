package pl.karol202.bphelper.interactors.usecases.table

import pl.karol202.bphelper.domain.entity.TableConfigurationError
import pl.karol202.bphelper.domain.service.TableConfigurationService
import pl.karol202.bphelper.domain.util.Either
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase0
import pl.karol202.bphelper.interactors.usecases.UseCase0
import pl.karol202.bphelper.interactors.usecases.member.GetMembersUseCase
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase

class CheckIfTableConfigurationPossibleUseCase(override val function: suspend () -> Either<TableConfigurationError, Unit>) :
	SuspendUseCase0<Either<TableConfigurationError, Unit>>

fun checkIfTableConfigurationPossibleUseCaseFactory(tableConfigurationService: TableConfigurationService,
                                                    getMembersUseCase: GetMembersUseCase,
                                                    getSettingsUseCase: GetSettingsUseCase) =
	CheckIfTableConfigurationPossibleUseCase {
		tableConfigurationService.isConfigurationPossible(
			tableConfigurationType = getSettingsUseCase().tableConfigurationType,
			members = getMembersUseCase()
		)
	}
