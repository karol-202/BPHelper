package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.GetPrepTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.preptimer.GetPrepTimerFinishEventFlowUseCase

class GetPrepTimerFinishEventFlowUseCaseImpl(private val prepTimerService: PrepTimerService) :
	GetPrepTimerFinishEventFlowUseCase
{
	override fun invoke() = prepTimerService.timerFinishEvent
}
