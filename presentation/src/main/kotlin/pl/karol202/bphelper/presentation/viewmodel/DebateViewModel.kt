package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.presentation.viewdata.RecordingEventViewData
import pl.karol202.bphelper.presentation.viewdata.RecordingStatusViewData
import pl.karol202.bphelper.presentation.viewdata.TimerStatusViewData
import kotlin.time.Duration

interface DebateViewModel : ViewModel
{
	val timerValue: Flow<Duration>
	val timerStatus: Flow<TimerStatusViewData>
	val timerOvertime: Flow<Boolean>

	val recordingStatus: Flow<RecordingStatusViewData>
	val recordingEvent: Flow<RecordingEventViewData>

	val currentRecordingStatus: RecordingStatusViewData

	fun toggleTimer()

	fun resetTimer()

	fun startRecording(recordingName: String)

	fun stopRecording()

	fun isRecordingNameAvailable(recordingName: String): Boolean
}
