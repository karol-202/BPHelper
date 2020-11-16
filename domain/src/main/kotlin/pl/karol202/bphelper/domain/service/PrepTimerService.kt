package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface PrepTimerService
{
	val timerValue: Flow<Duration>
	val timerActive: Flow<Boolean>

	fun start()

	fun stop()

	fun setDuration(duration: Duration)
}
