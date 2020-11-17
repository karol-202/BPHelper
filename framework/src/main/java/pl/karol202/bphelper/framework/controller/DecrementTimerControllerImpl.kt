package pl.karol202.bphelper.framework.controller

import android.os.CountDownTimer
import pl.karol202.bphelper.data.controller.DecrementTimerController

class DecrementTimerControllerImpl(private val durationMillis: Long,
                                   private val interval: Long,
                                   private val onTick: (Long) -> Unit,
                                   private val onFinish: () -> Unit) : DecrementTimerController
{
	object Factory : DecrementTimerController.Factory
	{
		override fun create(durationMillis: Long, interval: Long, onTick: (Long) -> Unit, onFinish: () -> Unit) =
			DecrementTimerControllerImpl(durationMillis, interval, onTick, onFinish)
	}

	private val timer = object : CountDownTimer(durationMillis, interval) {
		override fun onTick(millisUntilFinished: Long) = this@DecrementTimerControllerImpl.onTick(millisUntilFinished)

		override fun onFinish() = this@DecrementTimerControllerImpl.onFinish()
	}

	override fun start() = timer.start().let { }

	override fun stop() = timer.cancel()
}
