package pl.karol202.bphelper.presentation.viewdata

import pl.karol202.bphelper.domain.model.Settings
import pl.karol202.bphelper.domain.model.TableConfigurationType
import kotlin.time.Duration
import kotlin.time.minutes
import kotlin.time.seconds

/*class SettingsViewData(val tableConfigurationType: TableConfigurationType,
                       val speechDuration: Duration,
                       val speechDurationMax: Duration,
                       val poiStart: Duration,
                       val poiEnd: Duration,
                       val poiStartBellEnabled: Boolean,
                       val poiEndBellEnabled: Boolean)
{
	enum class TableConfigurationType
	{
		TYPE_4X2,
		TYPE_2X3,
		TYPE_2X4,
	}
}

fun Settings.toViewData() = SettingsViewData(
	tableConfigurationType = tableConfigurationType.toViewData(),
	speechDuration = speechDuration,
	speechDurationMax = speechDurationMax,
	poiStart = poiStart,
	poiEnd = poiEnd,
	poiStartBellEnabled = poiStartBellEnabled,
	poiEndBellEnabled = poiEndBellEnabled
)

fun TableConfigurationType.toViewData() = when(this)
{
	TableConfigurationType.TYPE_4X2 -> SettingsViewData.TableConfigurationType.TYPE_4X2
	TableConfigurationType.TYPE_2X3 -> SettingsViewData.TableConfigurationType.TYPE_2X3
	TableConfigurationType.TYPE_2X4 -> SettingsViewData.TableConfigurationType.TYPE_2X4
}

fun SettingsViewData.TableConfigurationType.toModel() = when(this)
{
	SettingsViewData.TableConfigurationType.TYPE_4X2 -> TableConfigurationType.TYPE_4X2
	SettingsViewData.TableConfigurationType.TYPE_2X3 -> TableConfigurationType.TYPE_2X3
	SettingsViewData.TableConfigurationType.TYPE_2X4 -> TableConfigurationType.TYPE_2X4
}*/
