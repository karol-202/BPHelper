package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerPoiFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerValueFlowUseCase

class GetDebateTimerPoiFlowUseCaseImpl(private val debateTimerService: DebateTimerService) : GetDebateTimerPoiFlowUseCase
{
	override fun invoke() = debateTimerService.poi
}
