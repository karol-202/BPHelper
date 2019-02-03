package pl.karol202.bphelper

import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Duration(val timeInMillis: Long) : Parcelable, Comparable<Duration>
{
	companion object
	{
		val zero = create()!!
		val max = create(minutes = 59, seconds = 59, millis = 999)!!

		fun create(minutes: Int = 0,
		           seconds: Int = 0,
		           millis: Int = 0) =
			if(minutes in 0..59 && seconds in 0..59 && millis in 0..999)
				Duration((((minutes * 60) + seconds) * 1000) + millis)
			else null

		fun fromMillis(millis: Long) = Duration(millis).takeIf { it in zero..max }

		fun fromMillis(millis: Int) = Duration(millis.toLong()).takeIf { it in zero..max }
	}

	@IgnoredOnParcel val minutes = ((timeInMillis / 1000) / 60).toInt()
	@IgnoredOnParcel val seconds = ((timeInMillis / 1000) % 60).toInt()
	@IgnoredOnParcel val millis = (timeInMillis % 1000).toInt()

	constructor(timeInMillis: Int) : this(timeInMillis.toLong())

	fun format(context: Context): String = context.getString(R.string.time_format, minutes, seconds)

	override fun compareTo(other: Duration) = timeInMillis.compareTo(other.timeInMillis)

	operator fun plus(other: Duration) = fromMillis(timeInMillis + other.timeInMillis)

	operator fun minus(other: Duration) = fromMillis(timeInMillis - other.timeInMillis)
}

operator fun Duration?.plus(other: Duration?) = if(this != null && other != null) this + other else null

operator fun Duration?.minus(other: Duration?) = if(this != null && other != null) this - other else null
