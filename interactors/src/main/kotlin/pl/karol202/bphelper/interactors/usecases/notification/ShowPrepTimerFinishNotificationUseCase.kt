package pl.karol202.bphelper.interactors.usecases.notification

import pl.karol202.bphelper.domain.service.NotificationService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class ShowPrepTimerFinishNotificationUseCase(override val function: () -> Unit) : UseCase0<Unit>

fun showPrepTimerFinishNotificationUseCaseFactory(notificationService: NotificationService) =
	ShowPrepTimerFinishNotificationUseCase {
		notificationService.showPrepTimerFinishNotification()
	}
