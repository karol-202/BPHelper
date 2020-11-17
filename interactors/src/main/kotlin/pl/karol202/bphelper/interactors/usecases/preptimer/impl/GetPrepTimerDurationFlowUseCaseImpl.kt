package pl.karol202.bphelper.interactors.usecases.preptimer.impl

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.preptimer.GetPrepTimerDurationFlowUseCase

class GetPrepTimerDurationFlowUseCaseImpl(private val prepTimerService: PrepTimerService) : GetPrepTimerDurationFlowUseCase
{
	override fun invoke() = prepTimerService.timerValue
}
