package pl.karol202.bphelper.interactors.usecases.debate.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerPoiFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerValueFlowUseCase

class GetDebateTimerPoiFlowUseCaseImpl(private val debateTimerService: DebateTimerService) :
	GetDebateTimerPoiFlowUseCase
{
	override fun invoke() = debateTimerService.poi
}
