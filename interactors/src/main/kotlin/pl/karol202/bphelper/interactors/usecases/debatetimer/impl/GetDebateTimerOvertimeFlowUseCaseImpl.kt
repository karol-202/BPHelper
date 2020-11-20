package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerOvertimeFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerPoiFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerValueFlowUseCase

class GetDebateTimerOvertimeFlowUseCaseImpl(private val debateTimerService: DebateTimerService) :
	GetDebateTimerOvertimeFlowUseCase
{
	override fun invoke() = debateTimerService.overtime
}
