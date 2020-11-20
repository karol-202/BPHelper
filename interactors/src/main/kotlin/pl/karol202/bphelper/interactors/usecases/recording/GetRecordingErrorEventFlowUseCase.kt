package pl.karol202.bphelper.interactors.usecases.recording

import kotlinx.coroutines.flow.Flow

interface GetRecordingErrorEventFlowUseCase
{
	operator fun invoke(): Flow<Unit>
}
