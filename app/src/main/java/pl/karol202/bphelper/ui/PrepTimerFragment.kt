package pl.karol202.bphelper.ui

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_prep_timer.*
import pl.karol202.bphelper.R
import pl.karol202.bphelper.Time
import pl.karol202.bphelper.ui.extensions.ctx

class PrepTimerFragment : BundledFragment()
{
	inner class Timer(initialTime: Time,
	            tickTime: Time) : CountDownTimer(initialTime.timeInMillis, tickTime.timeInMillis)
	{
		override fun onTick(millisUntilFinished: Long) = onTimerUpdate(Time.fromMillis(millisUntilFinished))

		override fun onFinish() = onTimerFinish()
	}

	companion object
	{
		private val DEFAULT_TIME = Time.create(minutes = 15)!!
		private val TICK_TIME = Time.create(millis = 100)!!
	}

	private var timerEnabled = false
	private var initialTime = DEFAULT_TIME
	private var timer: CountDownTimer? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
			inflater.inflate(R.layout.fragment_prep_timer, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		initText()
		initButton()
	}

	private fun initText()
	{
		textPrepTimer.text = initialTime.format(ctx)
	}

	private fun initButton()
	{
		buttonPrepTimerStart.setOnClickListener {
			timerEnabled = !timerEnabled
			buttonPrepTimerStart.setText(if(timerEnabled) R.string.button_prep_timer_stop else R.string.button_prep_timer_start)
			if(timerEnabled) startTimer()
			else stopTimer()
		}
	}

	override fun onDestroy()
	{
		super.onDestroy()
		stopTimer()
	}

	private fun startTimer()
	{
		val timer = Timer(initialTime, TICK_TIME)
		this.timer = timer
		timer.start()
	}

	private fun stopTimer()
	{
		timer?.cancel()
	}

	private fun onTimerUpdate(timeLeft: Time)
	{
		textPrepTimer.text = timeLeft.format(ctx)
	}

	private fun onTimerFinish()
	{
		textPrepTimer.setTextColor(Color.RED)
	}
}