package pl.karol202.bphelper.interactors.usecases.preptimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0
import kotlin.time.Duration

class GetPrepTimerDurationFlowUseCase(override val function: () -> Flow<Duration>) : UseCase0<Flow<Duration>>

fun getPrepTimerDurationFlowUseCaseFactory(prepTimerService: PrepTimerService) = GetPrepTimerDurationFlowUseCase {
	prepTimerService.value
}
