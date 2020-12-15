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
	// Look at overtime and poi
	private class Box(val duration: Duration)

	private var timer = createTimer(Duration.ZERO)

	override val value = MutableStateFlow(Duration.ZERO)
	override val active = MutableStateFlow(false)

	/* Boxing Duration is a workaround for KT-43249 (compiler bug related to wrong code generated for suspend lambdas with
	inline class parameters). Should be resolved in 1.4.30  */
	override val overtime = value.map { Box(it) }.combine(settingsRepository.settings) { value, settings ->
		when
		{
			value.duration >= settings.speechDurationMax -> Overtime.HARD
			value.duration >= settings.speechDuration -> Overtime.SOFT
			else -> Overtime.NONE
		}
	}.distinctUntilChanged()

	// As above
	override val poi = value.map { Box(it) }.combine(settingsRepository.settings) { value, settings ->
		when
		{
			value.duration >= settings.poiEnd -> PoiStatus.AFTER
			value.duration >= settings.poiStart -> PoiStatus.NOW
			else -> PoiStatus.BEFORE
		}
	}.distinctUntilChanged()

	override val overtimeBellEvent = overtime
		.filter { it != Overtime.NONE }

	override val poiBellEvent = poi
		.combine(settingsRepository.settings) { poi, settings -> poi to settings }
		.filter { (poi, settings) ->
			(poi == PoiStatus.NOW && settings.poiStartBellEnabled) || (poi == PoiStatus.AFTER && settings.poiEndBellEnabled)
		}
		.map { (poi, _) -> poi }

	override fun start()
	{
		active.value = true
		updateTimer(value.value)
		timer.start()
	}

	override fun pause()
	{
		timer.stop()
		active.value = false
	}

	override fun reset()
	{
		updateTimer(Duration.ZERO)
		active.value = false
	}

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
		incrementTimerControllerFactory.create(initialDurationMillis = initialDuration.inMilliseconds.toLong(),
		                                       interval = INTERVAL.inMilliseconds.toLong(),
		                                       onTick = this::onTick,
		                                       onFinish = this::onFinish)
}
