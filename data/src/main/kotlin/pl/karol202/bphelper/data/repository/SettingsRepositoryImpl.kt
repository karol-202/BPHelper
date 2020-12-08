package pl.karol202.bphelper.data.repository

import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.datastore.SettingsDataStore
import pl.karol202.bphelper.data.model.toModel
import pl.karol202.bphelper.data.model.toEntity
import pl.karol202.bphelper.domain.entity.Settings
import pl.karol202.bphelper.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settingsDataStore: SettingsDataStore) : SettingsRepository
{
	override val settings = settingsDataStore.settings.map { it.toEntity() }

	override fun getSettings() = settingsDataStore.getSettings().toEntity()

	override suspend fun setSettings(settings: Settings) = settingsDataStore.setSettings(settings.toModel())
}
