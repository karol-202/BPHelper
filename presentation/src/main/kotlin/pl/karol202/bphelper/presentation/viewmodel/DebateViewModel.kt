package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface DebateViewModel : ViewModel
{
	enum class TimerStatus
	{
		ACTIVE, PAUSED, STOPPED
	}

	val timerValue: Flow<Duration>
	val timerStatus: Flow<TimerStatus>
	val overtime: Flow<Boolean>

	fun toggle()

	fun reset()
}
