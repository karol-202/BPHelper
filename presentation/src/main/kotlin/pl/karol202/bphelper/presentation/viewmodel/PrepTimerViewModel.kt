package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface PrepTimerViewModel : ViewModel
{
	data class ValueSetDialogResponse(val initialValue: Duration)

	val timerValue: Flow<Duration>
	val timerActive: Flow<Boolean>
	val valueSetDialogResponse: Flow<ValueSetDialogResponse>

	fun toggle()

	fun requestValueSetDialog()

	fun setDuration(duration: Duration)
}
