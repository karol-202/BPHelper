package pl.karol202.bphelper.interactors.usecases.preptimer

import kotlinx.coroutines.flow.collect
import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase0
import pl.karol202.bphelper.interactors.usecases.notification.ShowPrepTimerFinishNotificationUseCase

class ShowNotificationWhenPrepTimerStopsUseCase(override val function: suspend () -> Unit) : SuspendUseCase0<Unit>

fun showNotificationWhenPrepTimerStopsUseCaseFactory(prepTimerService: PrepTimerService,
                                                     showPrepTimerFinishNotificationUseCase: ShowPrepTimerFinishNotificationUseCase) =
	ShowNotificationWhenPrepTimerStopsUseCase {
		prepTimerService.finishNotificationEvent.collect { showPrepTimerFinishNotificationUseCase() }
	}
