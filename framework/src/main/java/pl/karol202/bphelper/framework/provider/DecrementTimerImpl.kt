package pl.karol202.bphelper.framework.provider

import android.os.CountDownTimer
import pl.karol202.bphelper.data.timer.DecrementTimer
import kotlin.time.Duration

class DecrementTimerImpl(private val durationMillis: Long,
                         private val interval: Long,
                         private val onTick: (Long) -> Unit,
                         private val onFinish: () -> Unit) : DecrementTimer
{
	object Factory : DecrementTimer.Factory
	{
		override fun create(durationMillis: Long, interval: Long, onTick: (Long) -> Unit, onFinish: () -> Unit) =
			DecrementTimerImpl(durationMillis, interval, onTick, onFinish)
	}

	private val timer = object : CountDownTimer(durationMillis, interval) {
		override fun onTick(millisUntilFinished: Long) = this@DecrementTimerImpl.onTick(millisUntilFinished)

		override fun onFinish() = this@DecrementTimerImpl.onFinish()
	}

	override fun start() = timer.start().let { }

	override fun stop() = timer.cancel()
}
