package pl.karol202.bphelper.interactors.usecases.recording

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class GetRecordingActiveUseCase(override val function: () -> Boolean) : UseCase0<Boolean>

fun getRecordingActiveUseCaseFactory(recordingService: RecordingService) = GetRecordingActiveUseCase {
	recordingService.active.value
}
