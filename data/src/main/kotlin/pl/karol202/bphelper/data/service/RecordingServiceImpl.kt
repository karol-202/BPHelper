package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import pl.karol202.bphelper.data.datastore.RecordingDataStore
import pl.karol202.bphelper.data.controller.RecordingController
import pl.karol202.bphelper.data.model.NewRecordingModel
import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.domain.service.RecordingService.Event

class RecordingServiceImpl(private val recordingController: RecordingController,
                           private val recordingDataStore: RecordingDataStore) : RecordingService
{
	private val _event = MutableStateFlow<Event?>(null)

	override val recording = MutableStateFlow(false)
	override val event = _event.filterNotNull()

	override fun start(recordingName: String)
	{
		recording.value = true
		_event.value = null

		val recordingRequest = NewRecordingModel(recordingName, recordingController.recordingExtension, true)
		val createdRecording = recordingDataStore.createRecording(recordingRequest) ?: return onStop(error = true)
		recordingController.start(createdRecording.uri) { error -> onStop(error) }
	}

	override fun stop() = recordingController.stop()

	override fun isNameAvailable(name: String) = recordingDataStore.isNameAvailable(name)

	private fun onStop(error: Boolean)
	{
		recording.value = false
		_event.value = if(error) Event.ERROR else Event.FINISH
	}
}
