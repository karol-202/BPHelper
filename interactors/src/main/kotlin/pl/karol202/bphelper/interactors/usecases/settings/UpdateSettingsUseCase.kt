package pl.karol202.bphelper.interactors.usecases.settings

import pl.karol202.bphelper.domain.entity.Settings

interface UpdateSettingsUseCase
{
	suspend operator fun invoke(transform: Settings.() -> Settings)
}
