package pl.karol202.bphelper.data.entity

import kotlinx.serialization.Serializable
import pl.karol202.bphelper.domain.model.Settings
import pl.karol202.bphelper.domain.model.TableConfigurationType
import kotlin.time.milliseconds

@Serializable
class SettingsEntity(val tableConfigurationTypeName: String,
                     val speechDuration: Double,
                     val speechDurationMax: Double,
                     val poiStartEnabled: Boolean,
                     val poiStart: Double,
                     val poiEndEnabled: Boolean,
                     val poiEnd: Double)
{
	companion object
	{
		val DEFAULT = Settings.DEFAULT.toEntity()
	}
}

fun Settings.toEntity() = SettingsEntity(
	tableConfigurationTypeName = tableConfigurationType.name,
	speechDuration = speechDuration.inMilliseconds,
	speechDurationMax = speechDurationMax.inMilliseconds,
	poiStartEnabled = poiStartBellEnabled,
	poiStart = poiStart.inMilliseconds,
	poiEndEnabled = poiEndBellEnabled,
	poiEnd = poiEnd.inMilliseconds,
)
fun SettingsEntity.toModel() = Settings(
	tableConfigurationType = TableConfigurationType.findByName(tableConfigurationTypeName) ?: TableConfigurationType.DEFAULT,
	speechDuration = speechDuration.milliseconds,
	speechDurationMax = speechDurationMax.milliseconds,
	poiStartBellEnabled = poiStartEnabled,
	poiStart = poiStart.milliseconds,
	poiEndBellEnabled = poiEndEnabled,
	poiEnd = poiEnd.milliseconds,
)
