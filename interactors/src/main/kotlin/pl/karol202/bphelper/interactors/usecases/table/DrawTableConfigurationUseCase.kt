package pl.karol202.bphelper.interactors.usecases.table

import pl.karol202.bphelper.domain.entity.TableConfiguration
import pl.karol202.bphelper.domain.entity.TableConfigurationError
import pl.karol202.bphelper.domain.service.TableConfigurationService
import pl.karol202.bphelper.domain.util.Either
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase0
import pl.karol202.bphelper.interactors.usecases.UseCase0
import pl.karol202.bphelper.interactors.usecases.member.GetMembersUseCase
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase

class DrawTableConfigurationUseCase(override val function: suspend () -> Either<TableConfigurationError, TableConfiguration>) :
	SuspendUseCase0<Either<TableConfigurationError, TableConfiguration>>

fun drawTableConfigurationUseCaseFactory(tableConfigurationService: TableConfigurationService,
                                         getMembersUseCase: GetMembersUseCase,
                                         getSettingsUseCase: GetSettingsUseCase) =
	DrawTableConfigurationUseCase {
		tableConfigurationService.createConfiguration(
			tableConfigurationType = getSettingsUseCase().tableConfigurationType,
			members = getMembersUseCase()
		)
	}
