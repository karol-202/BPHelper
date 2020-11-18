package pl.karol202.bphelper.interactors.usecases.debate

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.DebateTimerService
import kotlin.time.Duration

interface GetDebateTimerPoiBellEventFlowUseCase
{
	operator fun invoke(): Flow<DebateTimerService.PoiStatus>
}
