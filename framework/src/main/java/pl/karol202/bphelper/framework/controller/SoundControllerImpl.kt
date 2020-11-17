package pl.karol202.bphelper.framework.controller

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.annotation.RequiresApi
import pl.karol202.bphelper.data.controller.SoundController
import pl.karol202.bphelper.framework.R
import pl.karol202.bphelper.framework.extensions.doOnApi

class SoundControllerImpl(context: Context) : SoundController
{
	private val soundPool = createSoundPool()

	private val soundBell1 = soundPool.load(context, R.raw.bell_1, 1)
	private val soundBell2 = soundPool.load(context, R.raw.bell_2, 1)
	private val soundBell3 = soundPool.load(context, R.raw.bell_3, 1)

	private fun createSoundPool() =
		doOnApi(Build.VERSION_CODES.LOLLIPOP, block = { createSoundPoolAPI21() }, fallback = { createSoundPoolLegacy() })

	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	private fun createSoundPoolAPI21() = SoundPool.Builder()
		.setMaxStreams(1)
		.setAudioAttributes(createAudioAttributes())
		.build()

	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	private fun createAudioAttributes() = AudioAttributes.Builder()
		.setUsage(AudioAttributes.USAGE_MEDIA)
		.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
		.build()

	@Suppress("DEPRECATION")
	private fun createSoundPoolLegacy() = SoundPool(1, AudioManager.STREAM_MUSIC, 0)

	override fun play(sound: SoundController.Sound)
	{
		val soundId = when(sound)
		{
			SoundController.Sound.SINGLE_BELL -> soundBell1
			SoundController.Sound.DOUBLE_BELL -> soundBell2
			SoundController.Sound.TRIPLE_BELL -> soundBell3
		}
		soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
	}

	fun release() = soundPool.release()
}
