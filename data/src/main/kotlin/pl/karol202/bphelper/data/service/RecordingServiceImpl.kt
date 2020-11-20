package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.datastore.RecordingDataStore
import pl.karol202.bphelper.data.controller.RecordingController
import pl.karol202.bphelper.data.entity.NewRecordingEntity
import pl.karol202.bphelper.domain.service.RecordingService

class RecordingServiceImpl(private val recordingController: RecordingController,
                           private val recordingDataStore: RecordingDataStore) : RecordingService
{
	private val _errorEvent = MutableStateFlow(Any())

	override val recording = MutableStateFlow(false)
	override val errorEvent = _errorEvent.map { Unit }

	override fun start(recordingName: String)
	{
		val recordingRequest = NewRecordingEntity(recordingName, recordingController.recordingExtension, true)
		val createdRecording = recordingDataStore.createRecording(recordingRequest) ?: return onStop(error = true)
		recording.value = true
		recordingController.start(createdRecording.uri) { error -> onStop(error) }
	}

	override fun stop() = recordingController.stop()

	private fun onStop(error: Boolean)
	{
		recording.value = false
		if(error) _errorEvent.value = Any()
	}
}
