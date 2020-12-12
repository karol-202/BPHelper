package pl.karol202.bphelper.interactors.usecases.debatetimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class GetDebateTimerActiveFlowUseCase(override val function: () -> Flow<Boolean>) : UseCase0<Flow<Boolean>>

fun getDebateTimerActiveFlowUseCaseFactory(debateTimerService: DebateTimerService) = GetDebateTimerActiveFlowUseCase {
	debateTimerService.active
}
