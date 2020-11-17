package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
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

	private val _timerValue = MutableStateFlow(INITIAL_DURATION)
	private val _timerActive = MutableStateFlow(false)

	override val timerValue: Flow<Duration> = _timerValue
	override val timerActive: Flow<Boolean> = _timerActive
	override val timerFinishEvent = _timerValue.filter { !it.isPositive() }

	override fun start()
	{
		_timerActive.value = true
		updateTimer(_timerValue.value)
		timer.start()
	}

	override fun stop()
	{
		timer.stop()
		_timerActive.value = false
	}

	override fun setDuration(duration: Duration) = updateTimer(duration)

	private fun onTick(durationMillis: Long)
	{
		_timerValue.value = durationMillis.milliseconds
	}

	private fun onFinish()
	{
		_timerValue.value = Duration.ZERO
		_timerActive.value = false
	}

	private fun updateTimer(initialDuration: Duration)
	{
		timer.stop()
		timer = createTimer(initialDuration)
		_timerValue.value = initialDuration
	}

	private fun createTimer(initialDuration: Duration) =
		decrementTimerControllerFactory.create(durationMillis = initialDuration.inMilliseconds.toLong(),
		                                       interval = INTERVAL.inMilliseconds.toLong(),
		                                       onTick = this::onTick,
		                                       onFinish = this::onFinish)
}
