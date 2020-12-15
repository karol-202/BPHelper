package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RecordingService
{
	enum class Event
	{
		FINISH, ERROR
	}

	val active: StateFlow<Boolean>
	val event: Flow<Event>

	fun start(recordingName: String)

	fun stop()

	fun isNameAvailable(name: String): Boolean
}
