package pl.karol202.bphelper.interactors.usecases.preptimer

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class StopPrepTimerUseCase(override val function: () -> Unit) : UseCase0<Unit>

fun stopPrepTimerUseCaseFactory(prepTimerService: PrepTimerService) = StopPrepTimerUseCase {
	prepTimerService.stop()
}
