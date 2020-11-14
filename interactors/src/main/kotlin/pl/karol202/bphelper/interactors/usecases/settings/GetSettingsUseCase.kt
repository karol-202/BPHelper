package pl.karol202.bphelper.interactors.usecases.settings

import pl.karol202.bphelper.domain.model.Settings

interface GetSettingsUseCase
{
	operator fun invoke(): Settings
}
