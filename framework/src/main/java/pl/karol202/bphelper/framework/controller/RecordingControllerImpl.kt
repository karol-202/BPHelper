package pl.karol202.bphelper.framework.controller

import android.content.Context
import pl.karol202.bphelper.data.controller.RecordingController
import pl.karol202.bphelper.framework.androidservice.RecordingAndroidService

class RecordingControllerImpl(private val context: Context) : RecordingController
{
	override fun start(filePath: String, onStop: (error: Boolean) -> Unit) =
		RecordingAndroidService.start(context, filePath, onStop)

	override fun stop() =
		RecordingAndroidService.stop(context)
}
