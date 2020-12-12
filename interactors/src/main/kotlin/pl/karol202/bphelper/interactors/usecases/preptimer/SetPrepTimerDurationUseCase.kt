package pl.karol202.bphelper.interactors.usecases.preptimer

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase1
import kotlin.time.Duration

class SetPrepTimerDurationUseCase(override val function: (Duration) -> Unit) : UseCase1<Duration, Unit>

fun setPrepTimerDurationUseCaseFactory(prepTimerService: PrepTimerService) = SetPrepTimerDurationUseCase { duration ->
	prepTimerService.setDuration(duration)
}
