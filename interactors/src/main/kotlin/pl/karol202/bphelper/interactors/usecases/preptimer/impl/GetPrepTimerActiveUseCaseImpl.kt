package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.GetPrepTimerActiveUseCase
import pl.karol202.bphelper.interactors.usecases.preptimer.StartPrepTimerUseCase

class GetPrepTimerActiveUseCaseImpl(private val prepTimerService: PrepTimerService) : GetPrepTimerActiveUseCase
{
	override fun invoke() = prepTimerService.timerActive
}
