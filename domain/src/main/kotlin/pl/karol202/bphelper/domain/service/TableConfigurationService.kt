package pl.karol202.bphelper.domain.service

import pl.karol202.bphelper.domain.entity.Member
import pl.karol202.bphelper.domain.entity.TableConfiguration
import pl.karol202.bphelper.domain.entity.TableConfigurationError
import pl.karol202.bphelper.domain.entity.TableConfigurationType
import pl.karol202.bphelper.domain.util.Either

interface TableConfigurationService
{
	fun createConfiguration(tableConfigurationType: TableConfigurationType, members: List<Member>):
			Either<TableConfigurationError, TableConfiguration>

	fun isConfigurationPossible(tableConfigurationType: TableConfigurationType, members: List<Member>):
			Either<TableConfigurationError, Unit>
}
