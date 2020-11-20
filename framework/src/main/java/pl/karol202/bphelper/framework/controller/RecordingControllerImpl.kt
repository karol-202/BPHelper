package pl.karol202.bphelper.framework.controller

import android.content.Context
import pl.karol202.bphelper.data.controller.RecordingController
import pl.karol202.bphelper.framework.androidservice.RecordingAndroidService

class RecordingControllerImpl(private val context: Context) : RecordingController
{
	override val recordingExtension = RecordingAndroidService.FILE_EXTENSION

	override fun start(recordingUri: String, onStop: (error: Boolean) -> Unit) =
		RecordingAndroidService.start(context, recordingUri, onStop)

	override fun stop() =
		RecordingAndroidService.stop(context)
}
