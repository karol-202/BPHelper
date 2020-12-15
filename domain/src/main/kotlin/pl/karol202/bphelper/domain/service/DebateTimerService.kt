package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

interface DebateTimerService
{
	enum class Overtime
	{
		NONE, SOFT, HARD
	}

	enum class PoiStatus
	{
		BEFORE, NOW, AFTER
	}

	val value: StateFlow<Duration>
	val active: StateFlow<Boolean>
	val overtime: Flow<Overtime>
	val poi: Flow<PoiStatus>
	val overtimeBellEvent: Flow<Overtime>
	val poiBellEvent: Flow<PoiStatus>

	fun start()

	fun pause()

	fun reset()
}
