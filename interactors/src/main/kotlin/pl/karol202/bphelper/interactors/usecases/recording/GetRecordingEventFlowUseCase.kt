package pl.karol202.bphelper.interactors.usecases.recording

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.RecordingService

interface GetRecordingEventFlowUseCase
{
	operator fun invoke(): Flow<RecordingService.Event>
}
