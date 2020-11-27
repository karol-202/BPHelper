package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow

interface SettingsViewModel : ViewModel
{
	/*val currentSettings: SettingsViewData

	fun setTableConfigurationType(type: SettingsViewData.TableConfigurationType)
	fun setSpeechDuration(duration: Duration)
	fun setSpeechDurationMax(duration: Duration)
	fun setStartPoiEnabled(enabled: Boolean)
	fun setStartPoi(duration: Duration)
	fun setEndPoiEnabled(enabled: Boolean)
	fun setEndPoi(duration: Duration)*/

	fun getString(key: String): String?

	fun getInt(key: String): Int

	fun getBoolean(key: String): Boolean

	fun putString(key: String, value: String?)

	fun putInt(key: String, value: Int)

	fun putBoolean(key: String, value: Boolean)
}
