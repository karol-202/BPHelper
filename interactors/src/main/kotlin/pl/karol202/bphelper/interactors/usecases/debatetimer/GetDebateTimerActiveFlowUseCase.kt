package pl.karol202.bphelper.interactors.usecases.debatetimer

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface GetDebateTimerActiveFlowUseCase
{
	operator fun invoke(): Flow<Boolean>
}
