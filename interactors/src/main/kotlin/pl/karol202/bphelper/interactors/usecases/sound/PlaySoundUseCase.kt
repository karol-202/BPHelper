package pl.karol202.bphelper.interactors.usecases.sound

import pl.karol202.bphelper.domain.service.SoundService
import pl.karol202.bphelper.interactors.usecases.UseCase1

class PlaySoundUseCase(override val function: (SoundService.Sound) -> Unit) : UseCase1<SoundService.Sound, Unit>

fun playSoundUseCaseFactory(soundService: SoundService) = PlaySoundUseCase { sound ->
	soundService.play(sound)
}
