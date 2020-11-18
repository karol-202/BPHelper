package pl.karol202.bphelper.interactors.usecases.debate.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debate.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debate.PauseDebateTimerUseCase
import pl.karol202.bphelper.interactors.usecases.debate.StartDebateTimerUseCase

class PauseDebateTimerUseCaseImpl(private val debateTimerService: DebateTimerService) : PauseDebateTimerUseCase
{
	override fun invoke() = debateTimerService.pause()
}
