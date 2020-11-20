package pl.karol202.bphelper.interactors.usecases.recording.impl

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.recording.StartRecordingUseCase

class StartRecordingUseCaseImpl(private val recordingService: RecordingService) : StartRecordingUseCase
{
	override fun invoke(recordingName: String) = recordingService.start(recordingName)
}
