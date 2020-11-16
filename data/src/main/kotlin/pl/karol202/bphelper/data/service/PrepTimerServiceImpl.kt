package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.karol202.bphelper.data.timer.DecrementTimer
import pl.karol202.bphelper.domain.service.PrepTimerService
import kotlin.time.Duration
import kotlin.time.milliseconds
import kotlin.time.minutes

private val INITIAL_DURATION = 15.minutes
private val INTERVAL = 100.milliseconds

class PrepTimerServiceImpl(private val decrementTimerFactory: DecrementTimer.Factory) : PrepTimerService
{
	private val _timerValue = MutableStateFlow(INITIAL_DURATION)
	private val _timerActive = MutableStateFlow(false)

	private var timer = createTimer(INITIAL_DURATION)

	override val timerValue: Flow<Duration> = _timerValue
	override val timerActive: Flow<Boolean> = _timerActive

	override fun start()
	{
		timer.start()
		_timerActive.value = true
	}

	override fun stop()
	{
		timer.stop()
		_timerActive.value = false
	}

	override fun setDuration(duration: Duration)
	{
		timer.stop()
		timer = createTimer(duration)
	}

	private fun onTick(durationMillis: Long)
	{
		_timerValue.value = durationMillis.milliseconds
	}

	private fun onFinish()
	{
		_timerValue.value = Duration.ZERO
		_timerActive.value = false
	}

	private fun createTimer(initialDuration: Duration) =
		decrementTimerFactory.create(durationMillis = initialDuration.inMilliseconds.toLong(),
		                             interval = INTERVAL.inMilliseconds.toLong(),
		                             onTick = this::onTick,
		                             onFinish = this::onFinish)
}
