package pl.karol202.bphelper.data.datastore

import pl.karol202.bphelper.data.model.NewRecordingModel
import pl.karol202.bphelper.data.model.RecordingModel

interface RecordingDataStore
{
	fun createRecording(recording: NewRecordingModel): RecordingModel?

	fun updateRecording(recording: RecordingModel)

	fun isNameAvailable(name: String): Boolean
}
