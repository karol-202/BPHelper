package pl.karol202.bphelper.interactors.usecases.debate.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerValueFlowUseCase

class GetDebateTimerValueFlowUseCaseImpl(private val debateTimerService: DebateTimerService) :
	GetDebateTimerValueFlowUseCase
{
	override fun invoke() = debateTimerService.value
}
