package pl.karol202.bphelper.data.datastore

import pl.karol202.bphelper.data.model.NewRecordingModel
import pl.karol202.bphelper.data.model.RecordingModel

interface RecordingDataStore
{
	fun createRecording(recording: NewRecordingModel): RecordingModel?

	fun isNameAvailable(name: String, extension: String): Boolean
}
