package pl.karol202.bphelper.domain.model

import kotlin.time.Duration
import kotlin.time.minutes
import kotlin.time.seconds

class Settings(val tableConfigurationType: TableConfigurationType,
               val speechDuration: Duration,
               val speechDurationMax: Duration,
               val poiStart: Duration,
               val poiEnd: Duration,
               val poiStartBellEnabled: Boolean,
               val poiEndBellEnabled: Boolean)
{
	companion object
	{
		val DEFAULT = Settings(tableConfigurationType = TableConfigurationType.DEFAULT,
		                       speechDuration = 7.minutes,
		                       speechDurationMax = 7.minutes + 15.seconds,
		                       poiStart = 1.minutes,
		                       poiEnd = 6.minutes,
		                       poiEndBellEnabled = true,
		                       poiStartBellEnabled = true)
	}
}
