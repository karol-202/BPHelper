package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.interactors.usecases.preptimer.*
import pl.karol202.bphelper.presentation.viewmodel.PrepTimerViewModel
import pl.karol202.bphelper.presentation.viewmodel.PrepTimerViewModel.ValueSetDialogResponse
import kotlin.time.Duration

class PrepTimerViewModelImpl(getPrepTimerValueFlowUseCase: GetPrepTimerValueFlowUseCase,
                             getPrepTimerActiveFlowUseCase: GetPrepTimerActiveFlowUseCase,
                             private val showNotificationWhenPrepTimerStopsUseCase: ShowNotificationWhenPrepTimerStopsUseCase,
                             private val togglePrepTimerUseCase: TogglePrepTimerUseCase,
                             private val getCanSetPrepTimerValueUseCase: GetCanSetPrepTimerValueUseCase,
                             private val getPrepTimerValueUseCase: GetPrepTimerValueUseCase,
                             private val setPrepTimerValueUseCase: SetPrepTimerValueUseCase) :
	BaseViewModel(), PrepTimerViewModel
{
	override val timerValue = getPrepTimerValueFlowUseCase()
	override val timerActive = getPrepTimerActiveFlowUseCase()
	override val valueSetDialogResponse = MutableSharedFlow<ValueSetDialogResponse>()

	init
	{
		showNotificationWhenTimerStops()
	}

	private fun showNotificationWhenTimerStops() = launch { showNotificationWhenPrepTimerStopsUseCase() }

	override fun toggle() = launch { togglePrepTimerUseCase() }

	override fun requestValueSetDialog() = launch {
		if(getCanSetPrepTimerValueUseCase())
			valueSetDialogResponse.emit(ValueSetDialogResponse(initialValue = getPrepTimerValueUseCase()))
	}

	override fun setDuration(duration: Duration) = launch { setPrepTimerValueUseCase(duration) }
}
