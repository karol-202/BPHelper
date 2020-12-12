package pl.karol202.bphelper.interactors.usecases.debatetimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0
import kotlin.time.Duration

class GetDebateTimerValueFlowUseCase(override val function: () -> Flow<Duration>) : UseCase0<Flow<Duration>>

fun getDebateTimerValueFlowUseCaseFactory(debateTimerService: DebateTimerService) = GetDebateTimerValueFlowUseCase {
	debateTimerService.value
}
