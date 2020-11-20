package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.ResetDebateTimerUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.StartDebateTimerUseCase

class ResetDebateTimerUseCaseImpl(private val debateTimerService: DebateTimerService) : ResetDebateTimerUseCase
{
	override fun invoke() = debateTimerService.reset()
}
