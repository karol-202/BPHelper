package pl.karol202.bphelper.interactors.usecases.recording

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.UseCase1

class IsRecordingNameAvailableUseCase(override val function: (String) -> Boolean) : UseCase1<String, Boolean>

fun isRecordingNameAvailableUseCaseFactory(recordingService: RecordingService) = IsRecordingNameAvailableUseCase { name ->
	recordingService.isNameAvailable(name)
}
