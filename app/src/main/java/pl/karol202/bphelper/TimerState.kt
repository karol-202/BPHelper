package pl.karol202.bphelper

import android.os.CountDownTimer
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlin.time.Duration
import kotlin.time.milliseconds
import kotlin.time.minutes
import kotlin.time.seconds

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
class TimerStateRunning(private var elapsedTime: Duration,
                        private var timeChecks: TimeChecks
) : TimerState
{
	companion object
	{
		private val TICK_INTERVAL = 100.milliseconds
		private val MAX_TIME = 59.minutes + 59.seconds

		fun create(stateContext: TimerStateContext,
		           elapsedTime: Duration = Duration.ZERO,
		           timeChecks: TimeChecks = TimeChecks()
		) =
				TimerStateRunning(elapsedTime, timeChecks).apply { setContext(stateContext) }
	}

	private inner class Timer(elapsedTime: Duration) :
		CountDownTimer((MAX_TIME - elapsedTime).inMilliseconds.toLong(),
		               TICK_INTERVAL.inMilliseconds.toLong())
	{
		override fun onTick(millisUntilFinished: Long) =
			onTimerUpdate(MAX_TIME - millisUntilFinished.milliseconds)

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
		timer = Timer(elapsedTime).apply { start() }
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
		if(!timeChecks.speechDuration && elapsedTime >= Settings.speechDuration && Settings.speechDuration != Duration.ZERO)
		{
			timeChecks = timeChecks.copy(speechDuration = true)
			stateContext.updateClockOvertime(true)
			stateContext.playBellSound(2)
		}
		if(!timeChecks.speechDurationMax && elapsedTime >= Settings.speechDurationMax && Settings.speechDurationMax != Duration.ZERO)
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
class TimerStatePaused(val elapsedTime: Duration,
                       val timeChecks: TimeChecks
) : TimerState
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
class TimerStateStopped : TimerState
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
		stateContext.updateClock(Duration.ZERO)
		stateContext.updateClockOvertime(false)
		stateContext.updateTimerButtons()
	}

	override fun onExiting() { }
}
