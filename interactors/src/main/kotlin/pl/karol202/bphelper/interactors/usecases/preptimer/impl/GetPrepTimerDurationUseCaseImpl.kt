package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.GetPrepTimerActiveUseCase
import pl.karol202.bphelper.interactors.usecases.preptimer.GetPrepTimerDurationUseCase
import pl.karol202.bphelper.interactors.usecases.preptimer.StartPrepTimerUseCase

class GetPrepTimerDurationUseCaseImpl(private val prepTimerService: PrepTimerService) : GetPrepTimerDurationUseCase
{
	override fun invoke() = prepTimerService.timerValue
}
