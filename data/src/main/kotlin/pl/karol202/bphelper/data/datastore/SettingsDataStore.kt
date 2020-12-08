package pl.karol202.bphelper.data.datastore

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.model.SettingsModel

interface SettingsDataStore
{
	val settings: Flow<SettingsModel>

	fun getSettings(): SettingsModel

	suspend fun setSettings(settings: SettingsModel)
}
