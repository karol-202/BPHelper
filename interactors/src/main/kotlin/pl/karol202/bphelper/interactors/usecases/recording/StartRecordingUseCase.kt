package pl.karol202.bphelper.interactors.usecases.recording

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase1

class StartRecordingUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun startRecordingUseCaseFactory(recordingService: RecordingService) = StartRecordingUseCase { recordingName ->
	recordingService.start(recordingName)
}

