package pl.karol202.bphelper.interactors.usecases.table

import pl.karol202.bphelper.domain.model.TableConfiguration
import pl.karol202.bphelper.domain.model.TableConfigurationError
import pl.karol202.bphelper.domain.util.Either

interface DrawTableConfigurationUseCase
{
	suspend operator fun invoke(): Either<TableConfigurationError, TableConfiguration>
}
