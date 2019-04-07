package pl.karol202.bphelper.debate

import android.os.CountDownTimer
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import pl.karol202.bphelper.Duration
import pl.karol202.bphelper.minus
import pl.karol202.bphelper.orThrow
import pl.karol202.bphelper.settings.Settings

interface TimerStateContext
{
	fun updateClock(duration: Duration)

	fun updateClockOvertime(overtime: Boolean)

	fun updateTimerButtons()

	fun playBellSound(times: Int)
}

interface TimerState : Parcelable
{
	fun setContext(stateContext: TimerStateContext)

	fun onEntering()

	fun onExiting()
}

@Parcelize
class TimerStateRunning private constructor(private var elapsedTime: Duration,
											private var timeChecks: TimeChecks) : TimerState
{
	companion object
	{
		private val TICK_INTERVAL = Duration.create(millis = 100)!!

		fun create(stateContext: TimerStateContext,
		           elapsedTime: Duration = Duration.zero,
		           timeChecks: TimeChecks = TimeChecks()) =
				TimerStateRunning(elapsedTime, timeChecks).apply { setContext(stateContext) }
	}

	private inner class Timer(elapsedTime: Duration,
	                          tickInterval: Duration) :
		CountDownTimer((Duration.max - elapsedTime).orThrow().timeInMillis.toLong(), tickInterval.timeInMillis.toLong())
	{
		override fun onTick(millisUntilFinished: Long) =
			onTimerUpdate((Duration.max - Duration.fromMillis(millisUntilFinished.toInt())).orThrow())

		override fun onFinish() = onTimerFinish()
	}

	@IgnoredOnParcel
	private lateinit var stateContext: TimerStateContext
	@IgnoredOnParcel
	private var timer: Timer? = null

	override fun setContext(stateContext: TimerStateContext)
	{
		this.stateContext = stateContext
	}

	override fun onEntering()
	{
		timer = Timer(elapsedTime, TICK_INTERVAL).apply { start() }
		stateContext.updateClock(elapsedTime)
		stateContext.updateClockOvertime(elapsedTime >= Settings.speechDuration)
		stateContext.updateTimerButtons()
	}

	override fun onExiting()
	{
		timer?.cancel()
		timer = null
	}

	fun onTimerUpdate(elapsedTime: Duration)
	{
		this.elapsedTime = elapsedTime
		stateContext.updateClock(elapsedTime)
		checkTime()
	}

	private fun checkTime()
	{
		if(!timeChecks.poiStart && elapsedTime >= Settings.poiStart && Settings.poiStartEnabled)
		{
			timeChecks = timeChecks.copy(poiStart = true)
			stateContext.playBellSound(1)
		}
		if(!timeChecks.poiEnd && elapsedTime >= Settings.poiEnd && Settings.poiEndEnabled)
		{
			timeChecks = timeChecks.copy(poiEnd = true)
			stateContext.playBellSound(1)
		}
		if(!timeChecks.speechDuration && elapsedTime >= Settings.speechDuration && Settings.speechDuration != Duration.zero)
		{
			timeChecks = timeChecks.copy(speechDuration = true)
			stateContext.updateClockOvertime(true)
			stateContext.playBellSound(2)
		}
		if(!timeChecks.speechDurationMax && elapsedTime >= Settings.speechDurationMax && Settings.speechDurationMax != Duration.zero)
		{
			timeChecks = timeChecks.copy(speechDurationMax = true)
			stateContext.playBellSound(3)
		}
	}

	//When timer reaches 59:59.999
	fun onTimerFinish() { }

	fun getElapsedTime() = elapsedTime

	fun getTimeChecks() = timeChecks
}

@Parcelize
class TimerStatePaused private constructor(val elapsedTime: Duration,
                                           val timeChecks: TimeChecks) : TimerState
{
	companion object
	{
		fun create(stateContext: TimerStateContext, elapsedTime: Duration, timeChecks: TimeChecks) =
			TimerStatePaused(elapsedTime, timeChecks).apply { setContext(stateContext) }
	}

	@IgnoredOnParcel
	private lateinit var stateContext: TimerStateContext

	override fun setContext(stateContext: TimerStateContext)
	{
		this.stateContext = stateContext
	}

	override fun onEntering()
	{
		stateContext.updateClock(elapsedTime)
		stateContext.updateClockOvertime(elapsedTime >= Settings.speechDuration)
		stateContext.updateTimerButtons()
	}

	override fun onExiting() { }
}

@Parcelize
class TimerStateStopped private constructor() : TimerState
{
	companion object
	{
		fun create(stateContext: TimerStateContext) = TimerStateStopped().apply { setContext(stateContext) }
	}

	@IgnoredOnParcel
	private lateinit var stateContext: TimerStateContext

	override fun setContext(stateContext: TimerStateContext)
	{
		this.stateContext = stateContext
	}

	override fun onEntering()
	{
		stateContext.updateClock(Duration.zero)
		stateContext.updateClockOvertime(false)
		stateContext.updateTimerButtons()
	}

	override fun onExiting() { }
}