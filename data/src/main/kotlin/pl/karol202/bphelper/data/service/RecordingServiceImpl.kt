package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.controller.RecordingController
import pl.karol202.bphelper.domain.service.RecordingService

class RecordingServiceImpl(private val recordingController: RecordingController) : RecordingService
{
	private val _errorEvent = MutableStateFlow(Any())

	override val recording = MutableStateFlow(false)
	override val errorEvent = _errorEvent.map { Unit }

	override fun start(filePath: String)
	{
		recording.value = true
		recordingController.start(filePath) { error -> onStop(error) }
	}

	override fun stop() = recordingController.stop()

	private fun onStop(error: Boolean)
	{
		recording.value = false
		if(error) _errorEvent.value = Any()
	}
}
