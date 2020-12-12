package pl.karol202.bphelper.interactors.usecases.debatetimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.interactors.usecases.UseCase0
import kotlin.time.Duration

class GetDebateTimerPoiFlowUseCase(override val function: () -> Flow<DebateTimerService.PoiStatus>) :
	UseCase0<Flow<DebateTimerService.PoiStatus>>

fun getDebateTimerPoiFlowUseCaseFactory(debateTimerService: DebateTimerService) = GetDebateTimerPoiFlowUseCase {
	debateTimerService.poi
}
