package pl.karol202.bphelper

import android.content.Context

data class Time(val timeInMillis: Long)
{
	companion object
	{
		fun create(minutes: Int = 0,
		           seconds: Int = 0,
		           millis: Int = 0) =
			if(minutes in 0..59 && seconds in 0..59 && millis in 0..999)
				Time.fromMillis((((minutes * 60) + seconds) * 1000) + millis)
			else null

		fun fromMillis(millis: Long) = Time(millis)

		fun fromMillis(millis: Int) = Time(millis.toLong())
	}

	private val minutes = (timeInMillis / 1000) / 60
	private val seconds = (timeInMillis / 1000) % 60
	private val millis = timeInMillis % 1000

	fun format(context: Context): String = context.getString(R.string.time_format, minutes, seconds)
}