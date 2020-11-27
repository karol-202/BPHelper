package pl.karol202.bphelper.interactors.usecases.settings

import pl.karol202.bphelper.domain.model.Settings

interface SetSettingsUseCase
{
	suspend operator fun invoke(settings: Settings)
}
