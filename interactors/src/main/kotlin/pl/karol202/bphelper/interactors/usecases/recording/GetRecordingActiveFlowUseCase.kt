package pl.karol202.bphelper.interactors.usecases.recording

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class GetRecordingActiveFlowUseCase(override val function: () -> Flow<Boolean>) : UseCase0<Flow<Boolean>>

fun getRecordingActiveFlowUseCaseFactory(recordingService: RecordingService) = GetRecordingActiveFlowUseCase {
	recordingService.active
}
