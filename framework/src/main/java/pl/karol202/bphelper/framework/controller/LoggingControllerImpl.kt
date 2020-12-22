package pl.karol202.bphelper.framework.controller

import com.google.firebase.crashlytics.FirebaseCrashlytics
import pl.karol202.bphelper.data.controller.LoggingController

class LoggingControllerImpl : LoggingController
{
	override fun log(throwable: Throwable)
	{
		throwable.printStackTrace()
		FirebaseCrashlytics.getInstance().recordException(throwable)
	}
}
