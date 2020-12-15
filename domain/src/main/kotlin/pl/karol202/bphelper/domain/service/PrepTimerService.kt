package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

interface PrepTimerService
{
	val value: StateFlow<Duration>
	val active: StateFlow<Boolean>
	val finishNotificationEvent: Flow<Unit>

	fun start()

	fun stop()

	fun setValue(duration: Duration)
}
