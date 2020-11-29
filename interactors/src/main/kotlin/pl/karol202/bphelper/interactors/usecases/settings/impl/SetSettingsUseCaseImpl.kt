package pl.karol202.bphelper.interactors.usecases.settings.impl

import pl.karol202.bphelper.domain.entity.Settings
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsFlowUseCase
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase
import pl.karol202.bphelper.interactors.usecases.settings.SetSettingsUseCase

class SetSettingsUseCaseImpl(private val settingsRepository: SettingsRepository) : SetSettingsUseCase
{
	override suspend fun invoke(settings: Settings) = settingsRepository.setSettings(settings)
}
