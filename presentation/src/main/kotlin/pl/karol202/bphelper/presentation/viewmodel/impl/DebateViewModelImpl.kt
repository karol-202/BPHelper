package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.domain.service.DebateTimerService.Overtime
import pl.karol202.bphelper.domain.service.SoundService.Sound
import pl.karol202.bphelper.interactors.usecases.debatetimer.*
import pl.karol202.bphelper.interactors.usecases.recording.*
import pl.karol202.bphelper.interactors.usecases.sound.PlaySoundUseCase
import pl.karol202.bphelper.presentation.util.collectIn
import pl.karol202.bphelper.presentation.viewdata.RecordingStatusViewData
import pl.karol202.bphelper.presentation.viewdata.TimerStatusViewData
import pl.karol202.bphelper.presentation.viewdata.toViewData
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel
import kotlin.time.Duration

class DebateViewModelImpl(getDebateTimerValueFlowUseCase: GetDebateTimerValueFlowUseCase,
                          getDebateTimerActiveFlowUseCase: GetDebateTimerActiveFlowUseCase,
                          getDebateTimerOvertimeFlowUseCase: GetDebateTimerOvertimeFlowUseCase,
                          private val getDebateTimerOvertimeBellEventFlowUseCase: GetDebateTimerOvertimeBellEventFlowUseCase,
                          private val getDebateTimerPoiBellEventFlowUseCase: GetDebateTimerPoiBellEventFlowUseCase,
                          private val startDebateTimerUseCase: StartDebateTimerUseCase,
                          private val pauseDebateTimerUseCase: PauseDebateTimerUseCase,
                          private val resetDebateTimerUseCase: ResetDebateTimerUseCase,
                          private val playSoundUseCase: PlaySoundUseCase,
                          getRecordingFlowUseCase: GetRecordingFlowUseCase,
                          getRecordingEventFlowUseCase: GetRecordingEventFlowUseCase,
                          private val startRecordingUseCase: StartRecordingUseCase,
                          private val stopRecordingUseCase: StopRecordingUseCase,
                          private val isRecordingNameAvailableUseCase: IsRecordingNameAvailableUseCase) :
	BaseViewModel(), DebateViewModel
{
	// Look at timerStatus
	private class Box(val duration: Duration)

	override val timerValue = getDebateTimerValueFlowUseCase()

	/* Boxing Duration is a workaround for KT-43249 (compiler bug related to wrong code generated for suspend lambdas with
	inline class parameters). Should be resolved in 1.4.30  */
	override val timerStatus = getDebateTimerActiveFlowUseCase().combine(timerValue.map { Box(it) }) { active, value ->
		when
		{
			active -> TimerStatusViewData.ACTIVE
			value.duration.isPositive() -> TimerStatusViewData.PAUSED
			else -> TimerStatusViewData.STOPPED
		}
	}.stateIn(viewModelScope, SharingStarted.Eagerly, TimerStatusViewData.STOPPED)

	override val timerOvertime = getDebateTimerOvertimeFlowUseCase().map { it != Overtime.NONE }

	override val recordingStatus = getRecordingFlowUseCase().map {
		if(it) RecordingStatusViewData.ACTIVE else RecordingStatusViewData.STOPPED
	}.stateIn(viewModelScope, SharingStarted.Eagerly, RecordingStatusViewData.STOPPED)

	override val recordingEvent = getRecordingEventFlowUseCase().map { it.toViewData() }

	override val currentRecordingStatus get() = recordingStatus.value

	init
	{
		setupOvertimeBell()
		setupPoiBell()
	}

	private fun setupOvertimeBell() = getDebateTimerOvertimeBellEventFlowUseCase().collectIn(viewModelScope) {
		when(it)
		{
			Overtime.SOFT -> playSoundUseCase(Sound.DOUBLE_BELL)
			Overtime.HARD -> playSoundUseCase(Sound.TRIPLE_BELL)
			else -> Unit
		}
	}

	private fun setupPoiBell() = getDebateTimerPoiBellEventFlowUseCase().collectIn(viewModelScope) {
		playSoundUseCase(Sound.SINGLE_BELL)
	}

	override fun toggleTimer() = launch {
		when(timerStatus.value)
		{
			TimerStatusViewData.ACTIVE -> pauseDebateTimerUseCase()
			else -> startDebateTimerUseCase()
		}
	}

	override fun resetTimer() = launch { resetDebateTimerUseCase() }

	override fun startRecording(recordingName: String) = launch { startRecordingUseCase(recordingName) }

	override fun stopRecording() = launch { stopRecordingUseCase() }

	override fun isRecordingNameAvailable(recordingName: String) = isRecordingNameAvailableUseCase(recordingName)
}
