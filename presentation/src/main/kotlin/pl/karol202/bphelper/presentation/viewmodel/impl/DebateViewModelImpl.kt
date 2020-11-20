package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.domain.service.DebateTimerService.Overtime
import pl.karol202.bphelper.domain.service.SoundService
import pl.karol202.bphelper.interactors.usecases.debate.*
import pl.karol202.bphelper.interactors.usecases.debatetimer.*
import pl.karol202.bphelper.interactors.usecases.sound.PlaySoundUseCase
import pl.karol202.bphelper.presentation.util.collectIn
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel.TimerStatus
import kotlin.time.Duration

class DebateViewModelImpl(getDebateTimerValueFlowUseCase: GetDebateTimerValueFlowUseCase,
                          getDebateTimerActiveFlowUseCase: GetDebateTimerActiveFlowUseCase,
                          getDebateTimerOvertimeFlowUseCase: GetDebateTimerOvertimeFlowUseCase,
                          private val getDebateTimerOvertimeBellEventFlowUseCase: GetDebateTimerOvertimeBellEventFlowUseCase,
                          private val getDebateTimerPoiBellEventFlowUseCase: GetDebateTimerPoiBellEventFlowUseCase,
                          private val startDebateTimerUseCase: StartDebateTimerUseCase,
                          private val pauseDebateTimerUseCase: PauseDebateTimerUseCase,
                          private val resetDebateTimerUseCase: ResetDebateTimerUseCase,
                          private val playSoundUseCase: PlaySoundUseCase) : BaseViewModel(), DebateViewModel
{
	// Look at timerStatus
	private class Box(val duration: Duration)

	override val timerValue = getDebateTimerValueFlowUseCase()

	/* Boxing Duration is a workaround for KT-43249 (compiler bug related to wrong code generated for suspend lambdas with
	inline class parameters). Should be resolved in 1.4.30  */
	override val timerStatus = getDebateTimerActiveFlowUseCase().combine(timerValue.map { Box(it) }) { active, value ->
		when
		{
			active -> TimerStatus.ACTIVE
			value.duration.isPositive() -> TimerStatus.PAUSED
			else -> TimerStatus.STOPPED
		}
	}.stateIn(viewModelScope, SharingStarted.Eagerly, TimerStatus.STOPPED)
	override val overtime = getDebateTimerOvertimeFlowUseCase().map { it != Overtime.NONE }

	init
	{
		setupOvertimeBell()
		setupPoiBell()
	}

	private fun setupOvertimeBell() = getDebateTimerOvertimeBellEventFlowUseCase().collectIn(viewModelScope) {
		when(it)
		{
			Overtime.SOFT -> playSoundUseCase(SoundService.Sound.DOUBLE_BELL)
			Overtime.HARD -> playSoundUseCase(SoundService.Sound.TRIPLE_BELL)
			else -> Unit
		}
	}

	private fun setupPoiBell() = getDebateTimerPoiBellEventFlowUseCase().collectIn(viewModelScope) {
		playSoundUseCase(SoundService.Sound.SINGLE_BELL)
	}

	override fun toggle() = launch {
		when(timerStatus.value)
		{
			TimerStatus.ACTIVE -> pauseDebateTimerUseCase()
			else -> startDebateTimerUseCase()
		}
	}

	override fun reset() = launch { resetDebateTimerUseCase() }
}
