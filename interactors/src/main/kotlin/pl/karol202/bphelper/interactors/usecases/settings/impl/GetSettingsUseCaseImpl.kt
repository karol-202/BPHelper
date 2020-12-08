package pl.karol202.bphelper.interactors.usecases.settings.impl

import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase

class GetSettingsUseCaseImpl(private val settingsRepository: SettingsRepository) : GetSettingsUseCase
{
	override fun invoke() = settingsRepository.getSettings()
}
