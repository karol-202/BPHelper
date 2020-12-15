package pl.karol202.bphelper.interactors.usecases.preptimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0
import kotlin.time.Duration

class GetPrepTimerValueFlowUseCase(override val function: () -> Flow<Duration>) : UseCase0<Flow<Duration>>

fun getPrepTimerValueFlowUseCaseFactory(prepTimerService: PrepTimerService) = GetPrepTimerValueFlowUseCase {
	prepTimerService.value
}
