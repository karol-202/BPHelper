package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.domain.service.DebateTimerService.Overtime
import pl.karol202.bphelper.interactors.usecases.debatetimer.*
import pl.karol202.bphelper.interactors.usecases.recording.*
import pl.karol202.bphelper.presentation.viewdata.RecordingNameValidityViewData
import pl.karol202.bphelper.presentation.viewdata.RecordingStatusViewData
import pl.karol202.bphelper.presentation.viewdata.TimerStatusViewData
import pl.karol202.bphelper.presentation.viewdata.toViewData
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel.DialogResponse
import kotlin.time.Duration

class DebateViewModelImpl(getDebateTimerValueFlowUseCase: GetDebateTimerValueFlowUseCase,
                          getDebateTimerActiveFlowUseCase: GetDebateTimerActiveFlowUseCase,
                          getDebateTimerOvertimeFlowUseCase: GetDebateTimerOvertimeFlowUseCase,
                          private val playBellWhenDebateTimerOvertimeUseCase: PlayBellWhenDebateTimerOvertimeUseCase,
                          private val playBellWhenDebateTimerPoiUseCase: PlayBellWhenDebateTimerPoiUseCase,
                          private val toggleDebateTimerUseCase: ToggleDebateTimerUseCase,
                          private val resetDebateTimerUseCase: ResetDebateTimerUseCase,
                          getRecordingActiveFlowUseCase: GetRecordingActiveFlowUseCase,
                          getRecordingEventFlowUseCase: GetRecordingEventFlowUseCase,
                          private val getRecordingActiveUseCase: GetRecordingActiveUseCase,
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
	}

	override val timerOvertime = getDebateTimerOvertimeFlowUseCase().map { it != Overtime.NONE }

	override val recordingStatus = getRecordingActiveFlowUseCase().map {
		if(it) RecordingStatusViewData.ACTIVE else RecordingStatusViewData.STOPPED
	}

	override val recordingEvent = getRecordingEventFlowUseCase().map { it.toViewData() }

	override val dialogResponse = MutableSharedFlow<DialogResponse>()

	init
	{
		setupOvertimeBell()
		setupPoiBell()
	}

	private fun setupOvertimeBell() = launch { playBellWhenDebateTimerOvertimeUseCase() }

	private fun setupPoiBell() = launch { playBellWhenDebateTimerPoiUseCase() }

	override fun toggleTimer() = launch { toggleDebateTimerUseCase() }

	override fun resetTimer() = launch { resetDebateTimerUseCase() }

	override fun requestRecordingToggle() = launch {
		if(getRecordingActiveUseCase()) dialogResponse.emit(DialogResponse.RecordingStopDialogResponse)
		else dialogResponse.emit(DialogResponse.FilenameChooseDialogResponse)
	}

	override fun startRecording(recordingName: String) = launch { startRecordingUseCase(recordingName) }

	override fun stopRecording() = launch { stopRecordingUseCase() }

	override fun checkRecordingNameValidity(name: String) =
		when
		{
			name.isEmpty() -> RecordingNameValidityViewData.EMPTY
			!isRecordingNameAvailableUseCase(name) -> RecordingNameValidityViewData.BUSY
			else -> RecordingNameValidityViewData.VALID
		}
}
