package pl.karol202.bphelper.domain.service

import pl.karol202.bphelper.domain.model.Member
import pl.karol202.bphelper.domain.model.TableConfiguration
import pl.karol202.bphelper.domain.model.TableConfigurationError
import pl.karol202.bphelper.domain.model.TableConfigurationType
import pl.karol202.bphelper.domain.util.Either

interface TableConfigurationService
{
	fun createConfiguration(tableConfigurationType: TableConfigurationType, members: List<Member>):
			Either<TableConfigurationError, TableConfiguration>

	fun isConfigurationPossible(tableConfigurationType: TableConfigurationType, members: List<Member>):
			Either<TableConfigurationError, Unit>
}
