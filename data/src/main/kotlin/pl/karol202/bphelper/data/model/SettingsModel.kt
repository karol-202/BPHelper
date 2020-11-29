package pl.karol202.bphelper.data.model

import kotlinx.serialization.Serializable
import pl.karol202.bphelper.domain.entity.Settings
import pl.karol202.bphelper.domain.entity.TableConfigurationType
import kotlin.time.milliseconds

@Serializable
class SettingsModel(val tableConfigurationTypeName: String,
                    val speechDuration: Double,
                    val speechDurationMax: Double,
                    val poiStartEnabled: Boolean,
                    val poiStart: Double,
                    val poiEndEnabled: Boolean,
                    val poiEnd: Double)
{
	companion object
	{
		val DEFAULT = Settings.DEFAULT.toModel()
	}
}

fun Settings.toModel() = SettingsModel(
	tableConfigurationTypeName = tableConfigurationType.name,
	speechDuration = speechDuration.inMilliseconds,
	speechDurationMax = speechDurationMax.inMilliseconds,
	poiStartEnabled = poiStartBellEnabled,
	poiStart = poiStart.inMilliseconds,
	poiEndEnabled = poiEndBellEnabled,
	poiEnd = poiEnd.inMilliseconds,
)
fun SettingsModel.toEntity() = Settings(
	tableConfigurationType = TableConfigurationType.findByName(tableConfigurationTypeName) ?: TableConfigurationType.DEFAULT,
	speechDuration = speechDuration.milliseconds,
	speechDurationMax = speechDurationMax.milliseconds,
	poiStartBellEnabled = poiStartEnabled,
	poiStart = poiStart.milliseconds,
	poiEndBellEnabled = poiEndEnabled,
	poiEnd = poiEnd.milliseconds,
)
