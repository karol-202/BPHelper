package pl.karol202.bphelper.interactors.usecases.settings

import pl.karol202.bphelper.domain.entity.Settings

interface GetSettingsUseCase
{
	suspend operator fun invoke(): Settings
}
