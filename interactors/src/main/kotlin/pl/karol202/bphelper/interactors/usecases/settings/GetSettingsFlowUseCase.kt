package pl.karol202.bphelper.interactors.usecases.settings

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.Settings

interface GetSettingsFlowUseCase
{
	operator fun invoke(): Flow<Settings>
}
