package pl.karol202.bphelper.interactors.usecases.recording.impl

import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.recording.GetRecordingEventFlowUseCase

class GetRecordingEventFlowUseCaseImpl(private val recordingService: RecordingService) : GetRecordingEventFlowUseCase
{
	override fun invoke() = recordingService.event
}
