package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.StopPrepTimerUseCase

class StopPrepTimerUseCaseImpl(private val prepTimerService: PrepTimerService) : StopPrepTimerUseCase
{
	override fun invoke() = prepTimerService.stop()
}
