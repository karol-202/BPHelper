package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerActiveFlowUseCase

class GetDebateTimerActiveFlowUseCaseImpl(private val debateTimerService: DebateTimerService) :
	GetDebateTimerActiveFlowUseCase
{
	override fun invoke() = debateTimerService.active
}
