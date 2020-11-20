package pl.karol202.bphelper.data.datastore

import pl.karol202.bphelper.data.entity.NewRecordingEntity
import pl.karol202.bphelper.data.entity.RecordingEntity

interface RecordingDataStore
{
	fun createRecording(recording: NewRecordingEntity): RecordingEntity?

	fun updateRecording(recording: RecordingEntity)

	fun isNameAvailable(name: String): Boolean
}
