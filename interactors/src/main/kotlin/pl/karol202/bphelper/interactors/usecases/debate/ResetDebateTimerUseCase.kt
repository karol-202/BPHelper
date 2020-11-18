package pl.karol202.bphelper.interactors.usecases.debate

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.service.DebateTimerService
import kotlin.time.Duration

interface ResetDebateTimerUseCase
{
	operator fun invoke()
}
