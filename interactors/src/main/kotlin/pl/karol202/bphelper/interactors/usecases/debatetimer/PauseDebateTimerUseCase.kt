package pl.karol202.bphelper.interactors.usecases.debatetimer

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.DebateTimerService
import kotlin.time.Duration

interface PauseDebateTimerUseCase
{
	operator fun invoke()
}
