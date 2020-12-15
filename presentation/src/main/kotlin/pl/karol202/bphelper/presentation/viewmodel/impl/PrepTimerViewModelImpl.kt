package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.interactors.usecases.preptimer.*
import pl.karol202.bphelper.presentation.viewmodel.PrepTimerViewModel
import pl.karol202.bphelper.presentation.viewmodel.PrepTimerViewModel.DurationSetDialogRequest
import kotlin.time.Duration

class PrepTimerViewModelImpl(getPrepTimerValueFlowUseCase: GetPrepTimerValueFlowUseCase,
                             getPrepTimerActiveFlowUseCase: GetPrepTimerActiveFlowUseCase,
                             private val showNotificationWhenTimerStopsUseCase: ShowNotificationWhenTimerStopsUseCase,
                             private val togglePrepTimerUseCase: TogglePrepTimerUseCase,
                             private val getCanSetPrepTimerValueUseCase: GetCanSetPrepTimerValueUseCase,
                             private val getPrepTimerValueUseCase: GetPrepTimerValueUseCase,
                             private val setPrepTimerValueUseCase: SetPrepTimerValueUseCase) :
	BaseViewModel(), PrepTimerViewModel
{
	override val timerValue = getPrepTimerValueFlowUseCase()
	override val timerActive = getPrepTimerActiveFlowUseCase()
	override val durationSetDialogRequest = MutableSharedFlow<DurationSetDialogRequest>()

	init
	{
		showNotificationWhenTimerStops()
	}

	private fun showNotificationWhenTimerStops() = launch { showNotificationWhenTimerStopsUseCase() }

	override fun toggle() = launch { togglePrepTimerUseCase() }

	override fun showDurationSetDialog() = launch {
		if(getCanSetPrepTimerValueUseCase())
			durationSetDialogRequest.emit(DurationSetDialogRequest(initialValue = getPrepTimerValueUseCase()))
	}

	override fun setDuration(duration: Duration) = launch { setPrepTimerValueUseCase(duration) }
}
