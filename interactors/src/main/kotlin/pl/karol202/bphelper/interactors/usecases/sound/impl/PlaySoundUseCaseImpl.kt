package pl.karol202.bphelper.interactors.usecases.sound.impl

import pl.karol202.bphelper.domain.service.SoundService
import pl.karol202.bphelper.interactors.usecases.sound.PlaySoundUseCase

class PlaySoundUseCaseImpl(private val soundService: SoundService) : PlaySoundUseCase
{
	override fun invoke(sound: SoundService.Sound) = soundService.play(sound)
}
