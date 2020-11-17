package pl.karol202.bphelper.interactors.usecases.preptimer

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface GetPrepTimerActiveFlowUseCase
{
	operator fun invoke(): Flow<Boolean>
}
