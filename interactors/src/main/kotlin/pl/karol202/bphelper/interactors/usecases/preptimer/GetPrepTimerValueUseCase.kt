package pl.karol202.bphelper.interactors.usecases.preptimer

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0
import kotlin.time.Duration

class GetPrepTimerValueUseCase(override val function: () -> Duration) : UseCase0<Duration>

fun getPrepTimerValueUseCaseFactory(prepTimerService: PrepTimerService) = GetPrepTimerValueUseCase {
	prepTimerService.value.value
}
