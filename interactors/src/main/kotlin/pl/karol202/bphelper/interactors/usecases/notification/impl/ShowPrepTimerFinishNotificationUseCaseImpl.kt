package pl.karol202.bphelper.interactors.usecases.notification.impl

import pl.karol202.bphelper.domain.service.NotificationService
import pl.karol202.bphelper.interactors.usecases.notification.ShowPrepTimerFinishNotificationUseCase

class ShowPrepTimerFinishNotificationUseCaseImpl(private val notificationService: NotificationService) :
	ShowPrepTimerFinishNotificationUseCase
{
	override fun invoke() = notificationService.showPrepTimerFinishNotification()
}
