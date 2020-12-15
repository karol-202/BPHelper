package pl.karol202.bphelper.interactors.usecases.preptimer

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class TogglePrepTimerUseCase(override val function: () -> Unit) : UseCase0<Unit>

fun togglePrepTimerUseCaseFactory(prepTimerService: PrepTimerService) = TogglePrepTimerUseCase {
	if(!prepTimerService.active.value) prepTimerService.start() else prepTimerService.stop()
}
