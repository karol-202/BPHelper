package pl.karol202.bphelper.interactors.usecases.recording

import kotlinx.coroutines.flow.Flow

interface GetRecordingFlowUseCase
{
	operator fun invoke(): Flow<Boolean>
}
