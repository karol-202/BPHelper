package pl.karol202.bphelper.data.service

import pl.karol202.bphelper.data.controller.LoggingController
import pl.karol202.bphelper.domain.service.LoggingService

class LoggingServiceImpl(private val loggingController: LoggingController) : LoggingService
{
	override fun log(throwable: Throwable) = loggingController.log(throwable)
}
