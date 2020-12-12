package pl.karol202.bphelper.interactors.usecases.preptimer

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class StartPrepTimerUseCase(override val function: () -> Unit) : UseCase0<Unit>

fun startPrepTimerUseCaseFactory(prepTimerService: PrepTimerService) = StartPrepTimerUseCase {
	prepTimerService.start()
}
