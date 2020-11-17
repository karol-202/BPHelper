package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import pl.karol202.bphelper.interactors.usecases.preptimer.*
import pl.karol202.bphelper.presentation.viewmodel.PrepTimerViewModel
import kotlin.time.Duration

class PrepTimerViewModelImpl(getPrepTimerDurationFlowUseCase: GetPrepTimerDurationFlowUseCase,
                             getPrepTimerActiveFlowUseCase: GetPrepTimerActiveFlowUseCase,
                             private val startPrepTimerUseCase: StartPrepTimerUseCase,
                             private val stopPrepTimerUseCase: StopPrepTimerUseCase,
                             private val setPrepTimerDurationUseCase: SetPrepTimerDurationUseCase) :
	BaseViewModel(), PrepTimerViewModel
{
	override val timerValue = getPrepTimerDurationFlowUseCase().onEach { currentTimerValue = it }
	override val timerActive = getPrepTimerActiveFlowUseCase().onEach { canSetDuration = !it }

	override var currentTimerValue = Duration.ZERO
		private set
	override var canSetDuration = false
		private set

	override fun toggle() = launch {
		if(!timerActive.first()) startPrepTimerUseCase() else stopPrepTimerUseCase()
	}

	override fun setDuration(duration: Duration) = launch { setPrepTimerDurationUseCase(duration) }
}
