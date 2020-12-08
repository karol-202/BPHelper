package pl.karol202.bphelper.presentation.viewmodel.impl

import pl.karol202.bphelper.domain.entity.TableConfigurationType
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase
import pl.karol202.bphelper.interactors.usecases.settings.UpdateSettingsUseCase
import pl.karol202.bphelper.presentation.viewdata.SettingsKeys
import pl.karol202.bphelper.presentation.viewmodel.SettingsViewModel
import kotlin.time.milliseconds

class SettingsViewModelImpl(private val getSettingsUseCase: GetSettingsUseCase,
                            private val updateSettingsUseCase: UpdateSettingsUseCase) : BaseViewModel(), SettingsViewModel
{
	override fun getString(key: String) = when(key)
	{
		SettingsKeys.TABLE_CONFIGURATION_TYPE -> getSettingsUseCase().tableConfigurationType.name
		else -> throwUnknownKey(key)
	}

	override fun getInt(key: String) = when(key)
	{
		SettingsKeys.SPEECH_DURATION -> getSettingsUseCase().speechDuration.inMilliseconds.toInt()
		SettingsKeys.SPEECH_DURATION_MAX -> getSettingsUseCase().speechDurationMax.inMilliseconds.toInt()
		SettingsKeys.POI_START -> getSettingsUseCase().poiStart.inMilliseconds.toInt()
		SettingsKeys.POI_END -> getSettingsUseCase().poiEnd.inMilliseconds.toInt()
		else -> throwUnknownKey(key)
	}

	override fun getBoolean(key: String) = when(key)
	{
		SettingsKeys.POI_START_ENABLED -> getSettingsUseCase().poiStartBellEnabled
		SettingsKeys.POI_END_ENABLED -> getSettingsUseCase().poiEndBellEnabled
		else -> throwUnknownKey(key)
	}

	override fun putString(key: String, value: String?) = launch {
		when(key)
		{
			SettingsKeys.TABLE_CONFIGURATION_TYPE -> updateSettingsUseCase {
				copy(tableConfigurationType = value?.let { TableConfigurationType.findByName(value) }
					?: throwIllegalValue(key, value))
			}
			else -> throwUnknownKey(key)
		}
	}

	override fun putInt(key: String, value: Int) = launch {
		when(key)
		{
			SettingsKeys.SPEECH_DURATION -> updateSettingsUseCase { copy(speechDuration = value.milliseconds) }
			SettingsKeys.SPEECH_DURATION_MAX -> updateSettingsUseCase { copy(speechDurationMax = value.milliseconds) }
			SettingsKeys.POI_START -> updateSettingsUseCase { copy(poiStart = value.milliseconds) }
			SettingsKeys.POI_END -> updateSettingsUseCase { copy(poiEnd = value.milliseconds) }
			else -> throwUnknownKey(key)
		}
	}

	override fun putBoolean(key: String, value: Boolean) = launch {
		when(key)
		{
			SettingsKeys.POI_START_ENABLED -> updateSettingsUseCase { copy(poiStartBellEnabled = value) }
			SettingsKeys.POI_END_ENABLED -> updateSettingsUseCase { copy(poiEndBellEnabled = value) }
			else -> throwUnknownKey(key)
		}
	}

	private fun throwUnknownKey(key: String): Nothing = throw IllegalArgumentException(key)

	private fun throwIllegalValue(key: String, value: Any?): Nothing = throw IllegalArgumentException("$key: $value")
}
