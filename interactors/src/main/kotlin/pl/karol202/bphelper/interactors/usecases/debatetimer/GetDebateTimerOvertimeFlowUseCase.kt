package pl.karol202.bphelper.interactors.usecases.debatetimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0
import kotlin.time.Duration

class GetDebateTimerOvertimeFlowUseCase(override val function: () -> Flow<DebateTimerService.Overtime>) :
	UseCase0<Flow<DebateTimerService.Overtime>>

fun getDebateTimerOvertimeFlowUseCaseFactory(debateTimerService: DebateTimerService) = GetDebateTimerOvertimeFlowUseCase {
	debateTimerService.overtime
}
