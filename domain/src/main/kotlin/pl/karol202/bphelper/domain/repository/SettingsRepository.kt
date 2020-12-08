package pl.karol202.bphelper.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.Settings

interface SettingsRepository
{
	val settings: Flow<Settings>

	fun getSettings(): Settings

	suspend fun setSettings(settings: Settings)
}
