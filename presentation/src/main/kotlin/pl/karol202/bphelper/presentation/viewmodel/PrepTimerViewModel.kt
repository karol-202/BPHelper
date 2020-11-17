package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface PrepTimerViewModel : ViewModel
{
	val timerValue: Flow<Duration>
	val timerActive: Flow<Boolean>

	val currentTimerValue: Duration
	val canSetDuration: Boolean

	fun toggle()

	fun setDuration(duration: Duration)
}
