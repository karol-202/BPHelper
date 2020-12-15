package pl.karol202.bphelper.interactors.usecases.debatetimer

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class ToggleDebateTimerUseCase(override val function: () -> Unit) : UseCase0<Unit>

fun toggleDebateTimerUseCaseFactory(debateTimerService: DebateTimerService) = ToggleDebateTimerUseCase {
	if(!debateTimerService.active.value) debateTimerService.start() else debateTimerService.pause()
}
