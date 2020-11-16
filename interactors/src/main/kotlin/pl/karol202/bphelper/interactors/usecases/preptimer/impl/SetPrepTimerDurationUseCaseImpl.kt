package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.SetPrepTimerDurationUseCase
import pl.karol202.bphelper.interactors.usecases.preptimer.StartPrepTimerUseCase
import kotlin.time.Duration

class SetPrepTimerDurationUseCaseImpl(private val prepTimerService: PrepTimerService) : SetPrepTimerDurationUseCase
{
	override fun invoke(duration: Duration) = prepTimerService.setDuration(duration)
}
