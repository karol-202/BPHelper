package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.controller.DecrementTimerController
import pl.karol202.bphelper.domain.service.PrepTimerService
import kotlin.time.Duration
import kotlin.time.milliseconds
import kotlin.time.minutes

private val INITIAL_DURATION = 15.minutes
private val INTERVAL = 100.milliseconds

class PrepTimerServiceImpl(private val decrementTimerControllerFactory: DecrementTimerController.Factory) : PrepTimerService
{
	private var timer = createTimer(INITIAL_DURATION)

	override val value = MutableStateFlow(INITIAL_DURATION)
	override val active = MutableStateFlow(false)

	override val finishNotificationEvent = value.filter { !it.isPositive() }.map { Unit }

	override fun start()
	{
		active.value = true
		updateTimer(value.value)
		timer.start()
	}

	override fun stop()
	{
		timer.stop()
		active.value = false
	}

	override fun setValue(duration: Duration) = updateTimer(duration)

	private fun onTick(durationMillis: Long)
	{
		value.value = durationMillis.milliseconds
	}

	private fun onFinish()
	{
		value.value = Duration.ZERO
		active.value = false
	}

	private fun updateTimer(initialDuration: Duration)
	{
		timer.stop()
		timer = createTimer(initialDuration)
		value.value = initialDuration
	}

	private fun createTimer(initialDuration: Duration) =
		decrementTimerControllerFactory.create(durationMillis = initialDuration.inMilliseconds.toLong(),
		                                       interval = INTERVAL.inMilliseconds.toLong(),
		                                       onTick = this::onTick,
		                                       onFinish = this::onFinish)
}
