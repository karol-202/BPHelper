package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.GetPrepTimerActiveFlowUseCase

class GetPrepTimerActiveFlowUseCaseImpl(private val prepTimerService: PrepTimerService) : GetPrepTimerActiveFlowUseCase
{
	override fun invoke() = prepTimerService.timerActive
}
