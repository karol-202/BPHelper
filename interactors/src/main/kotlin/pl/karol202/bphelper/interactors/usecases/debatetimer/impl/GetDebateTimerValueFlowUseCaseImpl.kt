package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerValueFlowUseCase

class GetDebateTimerValueFlowUseCaseImpl(private val debateTimerService: DebateTimerService) : GetDebateTimerValueFlowUseCase
{
	override fun invoke() = debateTimerService.value
}
