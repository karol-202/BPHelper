package pl.karol202.bphelper.domain.service

interface SoundService
{
	enum class Sound
	{
		SINGLE_BELL, DOUBLE_BELL, TRIPLE_BELL
	}

	fun play(sound: Sound)
}
