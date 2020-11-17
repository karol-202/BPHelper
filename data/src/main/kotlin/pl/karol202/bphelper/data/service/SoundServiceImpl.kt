package pl.karol202.bphelper.data.service

import pl.karol202.bphelper.data.controller.SoundController
import pl.karol202.bphelper.domain.service.SoundService

class SoundServiceImpl(private val soundController: SoundController) : SoundService
{
	override fun play(sound: SoundService.Sound) = soundController.play(sound.map())

	private fun SoundService.Sound.map() = when(this)
	{
		SoundService.Sound.SINGLE_BELL -> SoundController.Sound.SINGLE_BELL
		SoundService.Sound.DOUBLE_BELL -> SoundController.Sound.DOUBLE_BELL
		SoundService.Sound.TRIPLE_BELL -> SoundController.Sound.TRIPLE_BELL
	}
}
