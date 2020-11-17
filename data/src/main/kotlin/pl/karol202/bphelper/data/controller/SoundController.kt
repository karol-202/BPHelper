package pl.karol202.bphelper.data.controller

interface SoundController
{
	enum class Sound
	{
		SINGLE_BELL, DOUBLE_BELL, TRIPLE_BELL
	}

	fun play(sound: Sound)
}
