package pl.karol202.bphelper

import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Duration(val timeInMillis: Long) : Parcelable
{
	companion object
	{
		val zero = Duration(0)

		fun create(minutes: Int = 0,
		           seconds: Int = 0,
		           millis: Int = 0) =
			if(minutes in 0..59 && seconds in 0..59 && millis in 0..999)
				Duration.fromMillis((((minutes * 60) + seconds) * 1000) + millis)
			else null

		fun fromMillis(millis: Long) = Duration(millis)

		fun fromMillis(millis: Int) = Duration(millis.toLong())
	}

	@IgnoredOnParcel val minutes = ((timeInMillis / 1000) / 60).toInt()
	@IgnoredOnParcel val seconds = ((timeInMillis / 1000) % 60).toInt()
	@IgnoredOnParcel val millis = (timeInMillis % 1000).toInt()

	fun format(context: Context): String = context.getString(R.string.time_format, minutes, seconds)
}