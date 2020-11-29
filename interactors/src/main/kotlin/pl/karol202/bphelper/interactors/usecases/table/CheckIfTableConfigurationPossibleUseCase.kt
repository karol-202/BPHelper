package pl.karol202.bphelper.interactors.usecases.table

import pl.karol202.bphelper.domain.entity.TableConfigurationError
import pl.karol202.bphelper.domain.util.Either

interface CheckIfTableConfigurationPossibleUseCase
{
	suspend operator fun invoke(): Either<TableConfigurationError, Unit>
}
