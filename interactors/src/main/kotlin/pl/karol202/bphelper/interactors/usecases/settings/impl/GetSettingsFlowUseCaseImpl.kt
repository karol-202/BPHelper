package pl.karol202.bphelper.interactors.usecases.settings.impl

import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsFlowUseCase
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase

class GetSettingsFlowUseCaseImpl(private val settingsRepository: SettingsRepository) : GetSettingsFlowUseCase
{
	override fun invoke() = settingsRepository.settings
}
