package pl.karol202.bphelper.interactors.usecases.debate.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debate.ResetDebateTimerUseCase
import pl.karol202.bphelper.interactors.usecases.debate.StartDebateTimerUseCase

class ResetDebateTimerUseCaseImpl(private val debateTimerService: DebateTimerService) : ResetDebateTimerUseCase
{
	override fun invoke() = debateTimerService.reset()
}
