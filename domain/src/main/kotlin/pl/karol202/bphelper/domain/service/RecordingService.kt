package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow

interface RecordingService
{
	enum class Event
	{
		FINISH, ERROR
	}

	val recording: Flow<Boolean>
	val event: Flow<Event>

	fun start(recordingName: String)

	fun stop()

	fun isNameAvailable(name: String): Boolean
}
