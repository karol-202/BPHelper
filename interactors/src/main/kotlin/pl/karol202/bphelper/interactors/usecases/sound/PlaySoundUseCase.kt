package pl.karol202.bphelper.interactors.usecases.sound

import pl.karol202.bphelper.domain.service.SoundService

interface PlaySoundUseCase
{
	operator fun invoke(sound: SoundService.Sound)
}
