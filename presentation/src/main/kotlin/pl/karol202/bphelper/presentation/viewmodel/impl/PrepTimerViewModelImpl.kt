package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.interactors.usecases.notification.ShowPrepTimerFinishNotificationUseCase
import pl.karol202.bphelper.interactors.usecases.preptimer.*
import pl.karol202.bphelper.presentation.util.collectIn
import pl.karol202.bphelper.presentation.viewmodel.PrepTimerViewModel
import kotlin.time.Duration

class PrepTimerViewModelImpl(getPrepTimerDurationFlowUseCase: GetPrepTimerDurationFlowUseCase,
                             getPrepTimerActiveFlowUseCase: GetPrepTimerActiveFlowUseCase,
                             private val getPrepTimerFinishNotificationEventFlowUseCase: GetPrepTimerFinishNotificationEventFlowUseCase,
                             private val startPrepTimerUseCase: StartPrepTimerUseCase,
                             private val stopPrepTimerUseCase: StopPrepTimerUseCase,
                             private val setPrepTimerDurationUseCase: SetPrepTimerDurationUseCase,
                             private val showPrepTimerFinishNotificationUseCase: ShowPrepTimerFinishNotificationUseCase) :
	BaseViewModel(), PrepTimerViewModel
{
	override val timerValue = getPrepTimerDurationFlowUseCase().stateIn(viewModelScope, SharingStarted.Eagerly, Duration.ZERO)
	override val timerActive = getPrepTimerActiveFlowUseCase().stateIn(viewModelScope, SharingStarted.Eagerly, false)

	override val currentTimerValue get() = timerValue.value
	override val canSetDuration get() = !timerActive.value

	init
	{
		showNotificationWhenTimerStops()
	}

	private fun showNotificationWhenTimerStops() =
		getPrepTimerFinishNotificationEventFlowUseCase().collectIn(viewModelScope) { showPrepTimerFinishNotificationUseCase() }

	override fun toggle() = launch {
		if(!timerActive.first()) startPrepTimerUseCase() else stopPrepTimerUseCase()
	}

	override fun setDuration(duration: Duration) = launch { setPrepTimerDurationUseCase(duration) }
}
