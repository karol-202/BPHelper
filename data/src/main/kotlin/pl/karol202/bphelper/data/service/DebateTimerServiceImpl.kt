package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.data.controller.DecrementTimerController
import pl.karol202.bphelper.data.controller.IncrementTimerController
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.domain.service.DebateTimerService
import pl.karol202.bphelper.domain.service.DebateTimerService.Overtime
import pl.karol202.bphelper.domain.service.DebateTimerService.PoiStatus
import pl.karol202.bphelper.domain.service.PrepTimerService
import kotlin.time.Duration
import kotlin.time.milliseconds
import kotlin.time.minutes

private val INTERVAL = 100.milliseconds

class DebateTimerServiceImpl(private val incrementTimerControllerFactory: IncrementTimerController.Factory,
                             settingsRepository: SettingsRepository) :
	DebateTimerService
{
	private var timer = createTimer(Duration.ZERO)

	private val _value = MutableStateFlow(Duration.ZERO)
	private val _active = MutableStateFlow(false)

	override val value: Flow<Duration> = _value
	override val active: Flow<Boolean> = _active
	override val overtime = value.zip(settingsRepository.settings) { value, settings ->
		when
		{
			value >= settings.speechDurationMax -> Overtime.HARD
			value >= settings.speechDuration -> Overtime.SOFT
			else -> Overtime.NONE
		}
	}.distinctUntilChanged()
	override val overtimeBellEvent = overtime.filter { it != Overtime.NONE }
	override val poi = value.zip(settingsRepository.settings) { value, settings ->
		when
		{
			value >= settings.poiEnd -> PoiStatus.AFTER
			value >= settings.poiStart -> PoiStatus.NOW
			else -> PoiStatus.BEFORE
		}
	}.distinctUntilChanged()
	override val poiBellEvent = poi.zip(settingsRepository.settings) { poi, settings -> poi to settings }
		.filter { (poi, settings) ->
			(poi == PoiStatus.NOW && settings.poiStartBellEnabled) || (poi == PoiStatus.AFTER && settings.poiEndBellEnabled)
		}
		.map { (poi, _) -> poi }

	override fun start()
	{
		_active.value = true
		updateTimer(_value.value)
		timer.start()
	}

	override fun pause()
	{
		timer.stop()
		_active.value = false
	}

	override fun reset() = updateTimer(Duration.ZERO)

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
		incrementTimerControllerFactory.create(initialDurationMillis = initialDuration.inMilliseconds.toLong(),
		                                       interval = INTERVAL.inMilliseconds.toLong(),
		                                       onTick = this::onTick,
		                                       onFinish = this::onFinish)
}
