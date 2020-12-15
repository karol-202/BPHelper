package pl.karol202.bphelper.interactors.usecases.preptimer

import kotlinx.coroutines.flow.collect
import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase0
import pl.karol202.bphelper.interactors.usecases.notification.ShowPrepTimerFinishNotificationUseCase

class ShowNotificationWhenTimerStopsUseCase(override val function: suspend () -> Unit) : SuspendUseCase0<Unit>

fun showNotificationWhenTimerStopsUseCaseFactory(prepTimerService: PrepTimerService,
                                                 showPrepTimerFinishNotificationUseCase: ShowPrepTimerFinishNotificationUseCase) =
	ShowNotificationWhenTimerStopsUseCase {
		prepTimerService.finishNotificationEvent.collect { showPrepTimerFinishNotificationUseCase() }
	}
