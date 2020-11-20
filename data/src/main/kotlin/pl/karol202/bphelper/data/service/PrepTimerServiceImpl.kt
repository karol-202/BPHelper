package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.Flow
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

	private val _value = MutableStateFlow(INITIAL_DURATION)
	private val _active = MutableStateFlow(false)

	override val value: Flow<Duration> = _value
	override val active: Flow<Boolean> = _active
	override val finishNotificationEvent = _value.filter { !it.isPositive() }.map { Unit }

	override fun start()
	{
		_active.value = true
		updateTimer(_value.value)
		timer.start()
	}

	override fun stop()
	{
		timer.stop()
		_active.value = false
	}

	override fun setDuration(duration: Duration) = updateTimer(duration)

	private fun onTick(durationMillis: Long)
	{
		_value.value = durationMillis.milliseconds
	}

	private fun onFinish()
	{
		_value.value = Duration.ZERO
		_active.value = false
	}

	private fun updateTimer(initialDuration: Duration)
	{
		timer.stop()
		timer = createTimer(initialDuration)
		_value.value = initialDuration
	}

	private fun createTimer(initialDuration: Duration) =
		decrementTimerControllerFactory.create(durationMillis = initialDuration.inMilliseconds.toLong(),
		                                       interval = INTERVAL.inMilliseconds.toLong(),
		                                       onTick = this::onTick,
		                                       onFinish = this::onFinish)
}
