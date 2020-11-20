package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface PrepTimerService
{
	val value: Flow<Duration>
	val active: Flow<Boolean>
	val finishNotificationEvent: Flow<Unit>

	fun start()

	fun stop()

	fun setDuration(duration: Duration)
}
