package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.GetPrepTimerFinishNotificationEventFlowUseCase

class GetPrepTimerFinishNotificationEventFlowUseCaseImpl(private val prepTimerService: PrepTimerService) :
	GetPrepTimerFinishNotificationEventFlowUseCase
{
	override fun invoke() = prepTimerService.finishNotificationEvent
}
