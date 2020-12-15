package pl.karol202.bphelper.interactors.usecases.preptimer

import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class GetCanSetPrepTimerValueUseCase(override val function: () -> Boolean) : UseCase0<Boolean>

fun getCanSetPrepTimerValueUseCaseFactory(prepTimerService: PrepTimerService) = GetCanSetPrepTimerValueUseCase {
	!prepTimerService.active.value
}
