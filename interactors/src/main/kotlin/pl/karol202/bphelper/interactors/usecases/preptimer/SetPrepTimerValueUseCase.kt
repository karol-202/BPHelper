package pl.karol202.bphelper.interactors.usecases.preptimer

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase1
import kotlin.time.Duration

class SetPrepTimerValueUseCase(override val function: (Duration) -> Unit) : UseCase1<Duration, Unit>

fun setPrepTimerValueUseCaseFactory(prepTimerService: PrepTimerService) = SetPrepTimerValueUseCase { duration ->
	prepTimerService.setValue(duration)
}
