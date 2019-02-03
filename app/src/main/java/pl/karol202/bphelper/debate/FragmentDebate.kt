package pl.karol202.bphelper.debate

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_debate.*
import pl.karol202.bphelper.Duration
import pl.karol202.bphelper.R
import pl.karol202.bphelper.components.ExtendedFragment
import pl.karol202.bphelper.extensions.getColorCompat
import pl.karol202.bphelper.minus
import pl.karol202.bphelper.orThrow
import pl.karol202.bphelper.settings.Settings

private interface StateContext
{
	val ctx: Context

	fun updateClock(duration: Duration)

	fun updateClockOvertime(overtime: Boolean)

	fun updateTimerButton(@StringRes text: Int)

	fun playBellSound(times: Int)
}

class FragmentDebate : ExtendedFragment(), StateContext
{
	companion object
	{
		private val TICK_INTERVAL = Duration.create(millis = 100)!!
	}

	private interface State : Parcelable
	{
		fun setContext(stateContext: StateContext)

		fun onEntering()

		fun onExiting()
	}

	@Parcelize
	private class EnabledState private constructor(private var elapsedTime: Duration = Duration.zero,
	                                               private val timeChecks: TimeChecks = TimeChecks()) : State
	{
		companion object
		{
			fun create(stateContext: StateContext) = EnabledState().apply { setContext(stateContext) }
		}

		private inner class Timer(elapsedTime: Duration,
		                          tickInterval: Duration) :
			CountDownTimer((Duration.max - elapsedTime).orThrow().timeInMillis.toLong(), tickInterval.timeInMillis.toLong())
		{
			override fun onTick(millisUntilFinished: Long) = onTimerUpdate((Duration.max - Duration.fromMillis(millisUntilFinished.toInt())).orThrow())

			override fun onFinish() = onTimerFinish()
		}

		@Parcelize
		data class TimeChecks(var speechDuration: Boolean = false,
		                      var speechDurationMax: Boolean = false,
		                      var poiStart: Boolean = false,
		                      var poiEnd: Boolean = false) : Parcelable

		@IgnoredOnParcel private lateinit var stateContext: StateContext
		@IgnoredOnParcel private var timer: Timer? = null

		override fun setContext(stateContext: StateContext)
		{
			this.stateContext = stateContext
		}

		override fun onEntering()
		{
			timer = Timer(elapsedTime, TICK_INTERVAL).apply { start() }
			stateContext.updateClock(elapsedTime)
			stateContext.updateClockOvertime(elapsedTime >= Settings.speechDuration)
			stateContext.updateTimerButton(R.string.button_debate_timer_stop)
		}

		override fun onExiting()
		{
			timer?.cancel()
			timer = null

			stateContext.updateClockOvertime(false)
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
				timeChecks.poiStart = true
				stateContext.playBellSound(1)
			}
			if(!timeChecks.poiEnd && elapsedTime >= Settings.poiEnd && Settings.poiEndEnabled)
			{
				timeChecks.poiEnd = true
				stateContext.playBellSound(1)
			}
			if(!timeChecks.speechDuration && elapsedTime >= Settings.speechDuration)
			{
				timeChecks.speechDuration = true
				stateContext.updateClockOvertime(true)
				stateContext.playBellSound(2)
			}
			if(!timeChecks.speechDurationMax && elapsedTime >= Settings.speechDurationMax)
			{
				timeChecks.speechDurationMax = true
				stateContext.playBellSound(3)
			}
		}

		//When timer reaches 59:59.999
		fun onTimerFinish() { }
	}

	@Parcelize
	private class DisabledState private constructor(): State
	{
		companion object
		{
			fun create(stateContext: StateContext) = DisabledState().apply { setContext(stateContext) }
		}

		@IgnoredOnParcel
		private lateinit var stateContext: StateContext

		override fun setContext(stateContext: StateContext)
		{
			this.stateContext = stateContext
		}

		override fun onEntering()
		{
			stateContext.updateClock(Duration.zero)
			stateContext.updateTimerButton(R.string.button_debate_timer_start)
		}

		override fun onExiting() { }
	}

	override val ctx: Context
		get() = requireContext()

	private lateinit var bellPlayer: BellPlayer
	private var state by instanceStateOr<State>(DisabledState.create(this))

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_debate, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		bellPlayer = BellPlayer(ctx)
		state.setContext(this)
		state.onEntering()

		buttonDebateTime.setOnClickListener { changeState() }
	}

	private fun changeState()
	{
		state.onExiting()
		if(state is EnabledState) state = DisabledState.create(this)
		else if(state is DisabledState) state = EnabledState.create(this)
		state.onEntering()
	}

	override fun onDestroyView()
	{
		super.onDestroyView()
		bellPlayer.release()
		state.onExiting()
	}

	override fun updateClock(duration: Duration)
	{
		textDebateTimer.text = duration.format(ctx)
	}

	override fun updateClockOvertime(overtime: Boolean)
	{
		val color = if(overtime) R.color.text_timer_overtime else R.color.colorAccent
		textDebateTimer.setTextColor(ctx.getColorCompat(color))
	}

	override fun updateTimerButton(text: Int)
	{
		buttonDebateTime.setText(text)
	}

	override fun playBellSound(times: Int) = bellPlayer.play(times)
}