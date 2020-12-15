package pl.karol202.bphelper.interactors.usecases.debatetimer

import kotlinx.coroutines.flow.collect
import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.domain.service.SoundService
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase0
import pl.karol202.bphelper.interactors.usecases.sound.PlaySoundUseCase

class PlayBellWhenDebateTimerPoiUseCase(override val function: suspend () -> Unit) : SuspendUseCase0<Unit>

fun playBellWhenDebateTimerPoiUseCaseFactory(debateTimerService: DebateTimerService,
                                             playSoundUseCase: PlaySoundUseCase) = PlayBellWhenDebateTimerPoiUseCase {
	debateTimerService.poiBellEvent.collect { playSoundUseCase(SoundService.Sound.SINGLE_BELL) }
}
