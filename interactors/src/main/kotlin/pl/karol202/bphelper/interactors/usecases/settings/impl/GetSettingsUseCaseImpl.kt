package pl.karol202.bphelper.interactors.usecases.settings.impl

import kotlinx.coroutines.flow.first
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsFlowUseCase
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase

class GetSettingsUseCaseImpl(private val getSettingsFlowUseCase: GetSettingsFlowUseCase) : GetSettingsUseCase
{
	override suspend fun invoke() = getSettingsFlowUseCase().first()
}
