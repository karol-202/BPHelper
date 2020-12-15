package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface PrepTimerViewModel : ViewModel
{
	data class DurationSetDialogRequest(val initialValue: Duration)

	val timerValue: Flow<Duration>
	val timerActive: Flow<Boolean>
	val durationSetDialogRequest: Flow<DurationSetDialogRequest>

	fun toggle()

	fun showDurationSetDialog()

	fun setDuration(duration: Duration)
}
