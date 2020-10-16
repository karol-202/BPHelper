package pl.karol202.bphelper.preptimer

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
import kotlinx.android.synthetic.main.fragment_prep_timer.*
import pl.karol202.bphelper.Duration
import pl.karol202.bphelper.NotificationPreset
import pl.karol202.bphelper.R
import pl.karol202.bphelper.components.ExtendedFragment
import pl.karol202.bphelper.orThrow

private interface StateContext
{
	val ctx: Context

	fun updateClock(duration: Duration)

	fun updateButton(@StringRes text: Int)
}

class PrepTimerFragment : ExtendedFragment(),
                          StateContext,
                          DurationPickerFragment.OnDurationSetListener
{
	companion object
	{
		private val DEFAULT_DURATION = Duration.create(minutes = 15)!!
		private val TICK_INTERVAL = Duration.create(millis = 100)!!
	}

	object PrepTimeEndNotificationPreset : NotificationPreset()
	{
		override val title = R.string.notification_prep_time_end
	}

	private interface State : Parcelable
	{
		val initialDuration: Duration
		val allowsInitialDurationPicker: Boolean

		fun setContext(stateContext: StateContext)

		fun onInitialDurationSet(duration: Duration)

		fun onEntering()

		fun onExiting()
	}

	@Parcelize
	private class EnabledState private constructor(override val initialDuration: Duration,
	                                               private var timeLeft: Duration = initialDuration) : State
	{
		companion object
		{
			fun create(stateContext: StateContext, initialDuration: Duration) =
				EnabledState(initialDuration).apply { setContext(stateContext) }
		}

		private inner class Timer(initialDuration: Duration,
		                          tickInterval: Duration) :
			CountDownTimer(initialDuration.timeInMillis.toLong(), tickInterval.timeInMillis.toLong())
		{
			override fun onTick(millisUntilFinished: Long) = onTimerUpdate(Duration.fromMillis(millisUntilFinished.toInt()).orThrow())

			override fun onFinish() = onTimerFinish()
		}

		override val allowsInitialDurationPicker get() = false
		@IgnoredOnParcel private lateinit var stateContext: StateContext
		@IgnoredOnParcel private var timer: Timer? = null

		override fun setContext(stateContext: StateContext)
		{
			this.stateContext = stateContext
		}

		override fun onInitialDurationSet(duration: Duration) { }

		override fun onEntering()
		{
			timer = Timer(timeLeft, TICK_INTERVAL).apply { start() }
			stateContext.updateClock(timeLeft)
			stateContext.updateButton(R.string.button_prep_timer_stop)
		}

		override fun onExiting()
		{
			timer?.cancel()
			timer = null
		}

		private fun onTimerUpdate(timeLeft: Duration)
		{
			this.timeLeft = timeLeft
			stateContext.updateClock(timeLeft)
		}

		private fun onTimerFinish()
		{
			PrepTimeEndNotificationPreset.show(stateContext.ctx)
		}
	}

	@Parcelize
	private class DisabledState private constructor(override var initialDuration: Duration) : State
	{
		companion object
		{
			fun create(stateContext: StateContext, initialDuration: Duration) =
				DisabledState(initialDuration).apply { setContext(stateContext) }
		}

		override val allowsInitialDurationPicker get() = true
		@IgnoredOnParcel private lateinit var stateContext: StateContext

		override fun setContext(stateContext: StateContext)
		{
			this.stateContext = stateContext
		}

		override fun onInitialDurationSet(duration: Duration)
		{
			initialDuration = duration
			updateClockWithInitialDuration()
		}

		override fun onEntering()
		{
			updateClockWithInitialDuration()
			stateContext.updateButton(R.string.button_prep_timer_start)
		}

		override fun onExiting() { }

		private fun updateClockWithInitialDuration()
		{
			stateContext.updateClock(initialDuration)
		}
	}

	override val ctx get() = requireContext()

	private var state by instanceStateOr<State>(DisabledState.create(this, DEFAULT_DURATION))

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_prep_timer, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		state.setContext(this)
		state.onEntering()

		textPrepTimer.setOnClickListener { showDurationPickerDialog() }
		buttonPrepTimerStart.setOnClickListener { changeState() }
	}

	private fun showDurationPickerDialog()
	{
		if(!state.allowsInitialDurationPicker) return
		DurationPickerFragment.create(state.initialDuration, this).show(parentFragmentManager)
	}

	private fun changeState()
	{
		val initialDuration = state.initialDuration

		state.onExiting()
		if(state is EnabledState) state = DisabledState.create(this, initialDuration)
		else if(state is DisabledState) state = EnabledState.create(this, initialDuration)
		state.onEntering()
	}

	override fun onDestroyView()
	{
		super.onDestroyView()
		state.onExiting()
	}

	override fun updateClock(duration: Duration)
	{
		textPrepTimer.text = duration.format(ctx)
	}

	override fun updateButton(@StringRes text: Int)
	{
		buttonPrepTimerStart.setText(text)
	}

	override fun onDurationSet(duration: Duration) = state.onInitialDurationSet(duration)
}
