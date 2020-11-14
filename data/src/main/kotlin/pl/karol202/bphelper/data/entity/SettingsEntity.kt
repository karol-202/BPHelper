package pl.karol202.bphelper.data.entity

import kotlinx.serialization.Serializable
import pl.karol202.bphelper.domain.model.Settings
import pl.karol202.bphelper.domain.model.TableConfigurationType

@Serializable
class SettingsEntity(val tableConfigurationTypeName: String)
{
	companion object
	{
		val DEFAULT = Settings.DEFAULT.toEntity()
	}
}

fun Settings.toEntity() = SettingsEntity(
	tableConfigurationTypeName = tableConfigurationType.name
)
fun SettingsEntity.toModel() = Settings(
	tableConfigurationType = TableConfigurationType.findByName(tableConfigurationTypeName) ?: TableConfigurationType.DEFAULT
)
