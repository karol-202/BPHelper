package pl.karol202.bphelper.interactors.usecases.preptimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0
import kotlin.time.Duration

class GetPrepTimerActiveFlowUseCase(override val function: () -> Flow<Boolean>) : UseCase0<Flow<Boolean>>

fun getPrepTimerActiveFlowUseCaseFactory(prepTimerService: PrepTimerService) = GetPrepTimerActiveFlowUseCase {
	prepTimerService.active
}
