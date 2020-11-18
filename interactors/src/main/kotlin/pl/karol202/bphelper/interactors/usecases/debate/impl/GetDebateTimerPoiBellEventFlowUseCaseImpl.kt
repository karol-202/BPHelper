package pl.karol202.bphelper.interactors.usecases.debate.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerPoiBellEventFlowUseCase

class GetDebateTimerPoiBellEventFlowUseCaseImpl(private val debateTimerService: DebateTimerService) :
	GetDebateTimerPoiBellEventFlowUseCase
{
	override fun invoke() = debateTimerService.poiBellEvent
}
