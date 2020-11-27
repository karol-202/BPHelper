package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import pl.karol202.bphelper.domain.model.Settings
import pl.karol202.bphelper.domain.model.TableConfigurationType
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsFlowUseCase
import pl.karol202.bphelper.interactors.usecases.settings.SetSettingsUseCase
import pl.karol202.bphelper.presentation.viewdata.SettingsKeys
import pl.karol202.bphelper.presentation.viewmodel.SettingsViewModel
import kotlin.time.milliseconds

class SettingsViewModelImpl(getSettingsFlowUseCase: GetSettingsFlowUseCase,
                            private val setSettingsUseCase: SetSettingsUseCase) : BaseViewModel(), SettingsViewModel
{
	private val settingsFlow = getSettingsFlowUseCase().stateIn(viewModelScope, SharingStarted.Eagerly, Settings.DEFAULT)
	private val settings get() = settingsFlow.value

	override fun getString(key: String) = when(key)
	{
		SettingsKeys.TABLE_CONFIGURATION_TYPE -> settings.tableConfigurationType.name
		else -> throwUnknownKey(key)
	}

	override fun getInt(key: String) = when(key)
	{
		SettingsKeys.SPEECH_DURATION -> settings.speechDuration.inMilliseconds.toInt()
		SettingsKeys.SPEECH_DURATION_MAX -> settings.speechDurationMax.inMilliseconds.toInt()
		SettingsKeys.POI_START -> settings.poiStart.inMilliseconds.toInt()
		SettingsKeys.POI_END -> settings.poiEnd.inMilliseconds.toInt()
		else -> throwUnknownKey(key)
	}

	override fun getBoolean(key: String) = when(key)
	{
		SettingsKeys.POI_START_ENABLED -> settings.poiStartBellEnabled
		SettingsKeys.POI_END_ENABLED -> settings.poiEndBellEnabled
		else -> throwUnknownKey(key)
	}

	override fun putString(key: String, value: String?) = launch {
		when(key)
		{
			SettingsKeys.TABLE_CONFIGURATION_TYPE -> setSettingsUseCase(settings.copy(
				tableConfigurationType = value?.let { TableConfigurationType.findByName(value) }
					?: throwIllegalValue(key, value)
			))
			else -> throwUnknownKey(key)
		}
	}

	override fun putInt(key: String, value: Int) = launch {
		when(key)
		{
			SettingsKeys.SPEECH_DURATION -> setSettingsUseCase(settings.copy(speechDuration = value.milliseconds))
			SettingsKeys.SPEECH_DURATION_MAX -> setSettingsUseCase(settings.copy(speechDurationMax = value.milliseconds))
			SettingsKeys.POI_START -> setSettingsUseCase(settings.copy(poiStart = value.milliseconds))
			SettingsKeys.POI_END -> setSettingsUseCase(settings.copy(poiEnd = value.milliseconds))
			else -> throwUnknownKey(key)
		}
	}

	override fun putBoolean(key: String, value: Boolean) = launch {
		when(key)
		{
			SettingsKeys.POI_START_ENABLED -> setSettingsUseCase(settings.copy(poiStartBellEnabled = value))
			SettingsKeys.POI_END_ENABLED -> setSettingsUseCase(settings.copy(poiEndBellEnabled = value))
			else -> throwUnknownKey(key)
		}
	}

	private fun throwUnknownKey(key: String): Nothing = throw IllegalArgumentException(key)

	private fun throwIllegalValue(key: String, value: Any?): Nothing = throw IllegalArgumentException("$key: $value")

	/*override val currentSettings = currentSettingsModel.toViewData()

	override fun setTableConfigurationType(type: SettingsViewData.TableConfigurationType) = launch {
		setSettingsUseCase(currentSettingsModel.copy(tableConfigurationType = type.toModel()))
	}

	override fun setSpeechDuration(duration: Duration) = launch {
		setSettingsUseCase(currentSettingsModel.copy(speechDuration = duration))
	}

	override fun setSpeechDurationMax(duration: Duration) = launch {
		setSettingsUseCase(currentSettingsModel.copy(speechDurationMax = duration))
	}

	override fun setStartPoiEnabled(enabled: Boolean) = launch {
		setSettingsUseCase(currentSettingsModel.copy(poiStartBellEnabled = enabled))
	}

	override fun setStartPoi(duration: Duration) = launch {
		setSettingsUseCase(currentSettingsModel.copy(poiStart = duration))
	}

	override fun setEndPoiEnabled(enabled: Boolean) = launch {
		setSettingsUseCase(currentSettingsModel.copy(poiEndBellEnabled = enabled))
	}

	override fun setEndPoi(duration: Duration) = launch {
		setSettingsUseCase(currentSettingsModel.copy(poiEnd = duration))
	}*/
}
