package pl.karol202.bphelper.framework.controller

import android.os.CountDownTimer
import pl.karol202.bphelper.data.controller.DecrementTimerController
import pl.karol202.bphelper.data.controller.IncrementTimerController

private const val DURATION_MAX = Long.MAX_VALUE

class IncrementTimerControllerImpl(private val initialDurationMillis: Long,
                                   private val interval: Long,
                                   private val onTick: (Long) -> Unit,
                                   private val onFinish: () -> Unit) : IncrementTimerController
{
	object Factory : IncrementTimerController.Factory
	{
		override fun create(initialDurationMillis: Long, interval: Long, onTick: (Long) -> Unit, onFinish: () -> Unit) =
			IncrementTimerControllerImpl(initialDurationMillis, interval, onTick, onFinish)
	}

	private val timer = object : CountDownTimer(DURATION_MAX - initialDurationMillis, interval) {
		override fun onTick(millisUntilFinished: Long) =
			this@IncrementTimerControllerImpl.onTick(DURATION_MAX - millisUntilFinished)

		override fun onFinish() =
			this@IncrementTimerControllerImpl.onFinish()
	}

	override fun start() = timer.start().let { }

	override fun stop() = timer.cancel()
}
