package pl.karol202.bphelper.interactors.usecases.recording.impl

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.recording.GetRecordingFlowUseCase
import pl.karol202.bphelper.interactors.usecases.recording.StartRecordingUseCase
import pl.karol202.bphelper.interactors.usecases.recording.StopRecordingUseCase

class GetRecordingFlowUseCaseImpl(private val recordingService: RecordingService) : GetRecordingFlowUseCase
{
	override fun invoke() = recordingService.recording
}
