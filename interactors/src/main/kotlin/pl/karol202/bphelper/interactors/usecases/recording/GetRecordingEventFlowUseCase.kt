package pl.karol202.bphelper.interactors.usecases.recording

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class GetRecordingEventFlowUseCase(override val function: () -> Flow<RecordingService.Event>) :
	UseCase0<Flow<RecordingService.Event>>

fun getRecordingEventFlowUseCaseFactory(recordingService: RecordingService) = GetRecordingEventFlowUseCase {
	recordingService.event
}
