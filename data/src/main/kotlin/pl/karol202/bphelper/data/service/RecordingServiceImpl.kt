package pl.karol202.bphelper.data.service

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
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
	override val active = MutableStateFlow(false)
	override val event = MutableSharedFlow<Event>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

	override fun start(recordingName: String)
	{
		active.value = true

		val recordingRequest = NewRecordingModel(recordingName, recordingController.recordingExtension, true)
		val createdRecording = recordingDataStore.createRecording(recordingRequest) ?: return onStop(error = true)
		recordingController.start(createdRecording.uri) { error -> onStop(error) }
	}

	override fun stop() = recordingController.stop()

	override fun isNameAvailable(name: String) = recordingDataStore.isNameAvailable(name)

	private fun onStop(error: Boolean)
	{
		active.value = false
		event.tryEmit(if(error) Event.ERROR else Event.FINISH)
	}
}
