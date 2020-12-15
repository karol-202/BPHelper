package pl.karol202.bphelper.interactors.usecases.debatetimer

import kotlinx.coroutines.flow.collect
import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.domain.service.SoundService
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase0
import pl.karol202.bphelper.interactors.usecases.sound.PlaySoundUseCase

class PlayBellWhenDebateTimerOvertimeUseCase(override val function: suspend () -> Unit) : SuspendUseCase0<Unit>

fun playBellWhenDebateTimerOvertimeUseCaseFactory(debateTimerService: DebateTimerService,
                                                  playSoundUseCase: PlaySoundUseCase) =
	PlayBellWhenDebateTimerPoiUseCase {
		debateTimerService.overtimeBellEvent.collect { overtime ->
			when(overtime)
			{
				DebateTimerService.Overtime.SOFT -> playSoundUseCase(SoundService.Sound.DOUBLE_BELL)
				DebateTimerService.Overtime.HARD -> playSoundUseCase(SoundService.Sound.TRIPLE_BELL)
				else -> Unit
			}
		}
	}
