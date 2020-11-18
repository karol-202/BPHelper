package pl.karol202.bphelper.interactors.usecases.debate

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface GetDebateTimerValueFlowUseCase
{
	operator fun invoke(): Flow<Duration>
}
