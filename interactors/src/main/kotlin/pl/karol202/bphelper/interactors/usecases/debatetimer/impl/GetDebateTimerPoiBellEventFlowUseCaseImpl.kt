package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerPoiBellEventFlowUseCase

class GetDebateTimerPoiBellEventFlowUseCaseImpl(private val debateTimerService: DebateTimerService) :
	GetDebateTimerPoiBellEventFlowUseCase
{
	override fun invoke() = debateTimerService.poiBellEvent
}
