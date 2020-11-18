package pl.karol202.bphelper.data.datastore

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.entity.SettingsEntity

interface SettingsDataStore
{
	val settings: Flow<SettingsEntity>
}
