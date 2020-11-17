package pl.karol202.bphelper.data.service

import pl.karol202.bphelper.data.notification.NotificationManager
import pl.karol202.bphelper.domain.service.NotificationService

class NotificationServiceImpl(private val notificationManager: NotificationManager) : NotificationService
{
	override fun showPrepTimerFinishNotification() = notificationManager.showPrepTimerFinishNotification()
}
