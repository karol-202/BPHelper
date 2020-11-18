package pl.karol202.bphelper.interactors.usecases.debate.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerActiveFlowUseCase

class GetDebateTimerActiveFlowUseCaseImpl(private val debateTimerService: DebateTimerService) :
	GetDebateTimerActiveFlowUseCase
{
	override fun invoke() = debateTimerService.active
}
