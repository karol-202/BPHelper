package pl.karol202.bphelper.data.repository

import pl.karol202.bphelper.data.datastore.SettingsDataStore
import pl.karol202.bphelper.data.entity.toEntity
import pl.karol202.bphelper.data.entity.toModel
import pl.karol202.bphelper.domain.model.Settings
import pl.karol202.bphelper.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settingsDataStore: SettingsDataStore) : SettingsRepository
{
	override var settings: Settings
		get() = settingsDataStore.settings.toModel()
		set(value) { settingsDataStore.settings = value.toEntity() }
}
