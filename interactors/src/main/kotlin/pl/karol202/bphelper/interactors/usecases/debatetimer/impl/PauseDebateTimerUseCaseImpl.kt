package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.PauseDebateTimerUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.StartDebateTimerUseCase

class PauseDebateTimerUseCaseImpl(private val debateTimerService: DebateTimerService) : PauseDebateTimerUseCase
{
	override fun invoke() = debateTimerService.pause()
}
