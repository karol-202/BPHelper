package pl.karol202.bphelper.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.model.Settings

interface SettingsRepository
{
	val settings: Flow<Settings>

	suspend fun setSettings(settings: Settings)
}
