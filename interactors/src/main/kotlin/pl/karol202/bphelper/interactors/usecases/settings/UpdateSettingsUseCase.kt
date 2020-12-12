package pl.karol202.bphelper.interactors.usecases.settings

import pl.karol202.bphelper.domain.entity.Settings
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase1

class UpdateSettingsUseCase(override val function: suspend (Settings.() -> Settings) -> Unit) :
	SuspendUseCase1<Settings.() -> Settings, Unit>

fun updateSettingsUseCaseFactory(settingsRepository: SettingsRepository) = UpdateSettingsUseCase { transform ->
	settingsRepository.setSettings(settingsRepository.getSettings().transform())
}
