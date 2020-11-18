package pl.karol202.bphelper.data.repository

import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.datastore.SettingsDataStore
import pl.karol202.bphelper.data.entity.toEntity
import pl.karol202.bphelper.data.entity.toModel
import pl.karol202.bphelper.domain.model.Settings
import pl.karol202.bphelper.domain.repository.SettingsRepository

class SettingsRepositoryImpl(settingsDataStore: SettingsDataStore) : SettingsRepository
{
	override val settings = settingsDataStore.settings.map { it.toModel() }
}
