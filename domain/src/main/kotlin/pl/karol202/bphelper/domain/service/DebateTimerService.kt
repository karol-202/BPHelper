package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow
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

	val value: Flow<Duration>
	val active: Flow<Boolean>
	val overtime: Flow<Overtime>
	val overtimeBellEvent: Flow<Overtime>
	val poi: Flow<PoiStatus>
	val poiBellEvent: Flow<PoiStatus>

	fun start()

	fun pause()

	fun reset()
}
