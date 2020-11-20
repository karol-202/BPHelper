package pl.karol202.bphelper.interactors.usecases.recording.impl

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.recording.StartRecordingUseCase
import pl.karol202.bphelper.interactors.usecases.recording.StopRecordingUseCase

class StopRecordingUseCaseImpl(private val recordingService: RecordingService) : StopRecordingUseCase
{
	override fun invoke() = recordingService.stop()
}
