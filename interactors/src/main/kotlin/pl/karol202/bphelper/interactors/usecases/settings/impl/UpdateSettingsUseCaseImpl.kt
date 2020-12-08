package pl.karol202.bphelper.interactors.usecases.settings.impl

import pl.karol202.bphelper.domain.entity.Settings
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.interactors.usecases.settings.UpdateSettingsUseCase

class UpdateSettingsUseCaseImpl(private val settingsRepository: SettingsRepository) : UpdateSettingsUseCase
{
	override suspend fun invoke(transform: Settings.() -> Settings) =
		settingsRepository.setSettings(settingsRepository.getSettings().transform())
}
