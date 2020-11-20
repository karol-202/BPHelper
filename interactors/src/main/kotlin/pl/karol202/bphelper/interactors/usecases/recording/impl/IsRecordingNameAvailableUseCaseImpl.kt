package pl.karol202.bphelper.interactors.usecases.recording.impl

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.recording.IsRecordingNameAvailableUseCase

class IsRecordingNameAvailableUseCaseImpl(private val recordingService: RecordingService) : IsRecordingNameAvailableUseCase
{
	override fun invoke(name: String) = recordingService.isNameAvailable(name)
}
