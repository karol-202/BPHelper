package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.presentation.viewdata.RecordingEventViewData
import pl.karol202.bphelper.presentation.viewdata.RecordingNameValidityViewData
import pl.karol202.bphelper.presentation.viewdata.RecordingStatusViewData
import pl.karol202.bphelper.presentation.viewdata.TimerStatusViewData
import kotlin.time.Duration

interface DebateViewModel : ViewModel
{
	sealed class DialogResponse
	{
		object RecordingStopDialogResponse : DialogResponse()
		object FilenameChooseDialogResponse : DialogResponse()
	}

	val timerValue: Flow<Duration>
	val timerStatus: Flow<TimerStatusViewData>
	val timerOvertime: Flow<Boolean>

	val recordingStatus: Flow<RecordingStatusViewData>
	val recordingEvent: Flow<RecordingEventViewData>

	val dialogResponse: Flow<DialogResponse>

	fun toggleTimer()

	fun resetTimer()

	fun requestRecordingToggle()

	fun startRecording(recordingName: String)

	fun stopRecording()

	fun checkRecordingNameValidity(name: String): RecordingNameValidityViewData
}
