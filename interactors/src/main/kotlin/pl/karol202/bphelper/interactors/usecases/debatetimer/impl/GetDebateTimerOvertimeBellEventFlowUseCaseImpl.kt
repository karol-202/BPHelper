package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerOvertimeBellEventFlowUseCase

class GetDebateTimerOvertimeBellEventFlowUseCaseImpl(private val debateTimerService: DebateTimerService) :
	GetDebateTimerOvertimeBellEventFlowUseCase
{
	override fun invoke() = debateTimerService.overtimeBellEvent
}
