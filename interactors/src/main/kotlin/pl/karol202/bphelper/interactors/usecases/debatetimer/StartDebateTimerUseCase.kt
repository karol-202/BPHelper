package pl.karol202.bphelper.interactors.usecases.debatetimer

import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class StartDebateTimerUseCase(override val function: () -> Unit) : UseCase0<Unit>

fun startDebateTimerUseCaseFactory(debateTimerService: DebateTimerService) = StartDebateTimerUseCase {
	debateTimerService.start()
}
