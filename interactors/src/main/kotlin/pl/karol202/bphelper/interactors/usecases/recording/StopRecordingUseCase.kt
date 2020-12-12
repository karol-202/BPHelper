package pl.karol202.bphelper.interactors.usecases.recording

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class StopRecordingUseCase(override val function: () -> Unit) : UseCase0<Unit>

fun stopRecordingUseCaseFactory(recordingService: RecordingService) = StopRecordingUseCase {
	recordingService.stop()
}
