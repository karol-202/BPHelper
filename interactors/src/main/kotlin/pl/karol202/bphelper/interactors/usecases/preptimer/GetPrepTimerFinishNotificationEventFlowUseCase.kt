package pl.karol202.bphelper.interactors.usecases.preptimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0
import kotlin.time.Duration

class GetPrepTimerFinishNotificationEventFlowUseCase(override val function: () -> Flow<Unit>) : UseCase0<Flow<Unit>>

fun getPrepTimerFinishNotificationEventFlowUseCaseFactory(prepTimerService: PrepTimerService) =
	GetPrepTimerFinishNotificationEventFlowUseCase {
		prepTimerService.finishNotificationEvent
	}
