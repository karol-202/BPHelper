package pl.karol202.bphelper.interactors.usecases.debatetimer.impl

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.StartDebateTimerUseCase

class StartDebateTimerUseCaseImpl(private val debateTimerService: DebateTimerService) : StartDebateTimerUseCase
{
	override fun invoke() = debateTimerService.start()
}
