package pl.karol202.bphelper.interactors.usecases.settings

import pl.karol202.bphelper.domain.entity.Settings
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.interactors.usecases.UseCase0

class GetSettingsUseCase(override val function: () -> Settings) : UseCase0<Settings>

fun getSettingsUseCaseFactory(settingsRepository: SettingsRepository) = GetSettingsUseCase {
	settingsRepository.getSettings()
}
