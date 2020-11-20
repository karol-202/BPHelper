package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow

interface RecordingService
{
	val recording: Flow<Boolean>
	val errorEvent: Flow<Unit>

	fun start(recordingName: String)

	fun stop()
}
