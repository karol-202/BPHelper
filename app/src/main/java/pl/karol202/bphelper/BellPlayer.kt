package pl.karol202.bphelper

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.annotation.RequiresApi
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.doOnApi

class BellPlayer(context: Context)
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

	fun play(times: Int)
	{
		val sound = when(times)
		{
			1 -> soundBell1
			2 -> soundBell2
			3 -> soundBell3
			else -> throw IllegalArgumentException("Unsupported bell sound: $times.")
		}
		soundPool.play(sound, 1f, 1f, 0, 0, 1f)
	}

	fun release() = soundPool.release()
}
