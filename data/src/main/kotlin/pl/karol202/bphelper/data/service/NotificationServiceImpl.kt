package pl.karol202.bphelper.data.service

import pl.karol202.bphelper.data.controller.NotificationController
import pl.karol202.bphelper.domain.service.NotificationService

class NotificationServiceImpl(private val notificationController: NotificationController) : NotificationService
{
	override fun showPrepTimerFinishNotification() = notificationController.showPrepTimerFinishNotification()
}
