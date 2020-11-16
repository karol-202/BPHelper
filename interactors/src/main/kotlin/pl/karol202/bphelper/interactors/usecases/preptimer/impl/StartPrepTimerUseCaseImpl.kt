package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.StartPrepTimerUseCase

class StartPrepTimerUseCaseImpl(private val prepTimerService: PrepTimerService) : StartPrepTimerUseCase
{
	override fun invoke() = prepTimerService.start()
}
