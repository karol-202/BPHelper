package pl.karol202.bphelper.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.core.graphics.luminance
import androidx.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.time.Duration
import kotlin.time.milliseconds
import kotlin.time.minutes
import kotlin.time.seconds

object Settings
{
	class PreferencesDelegate<T>(private val read: SharedPreferences.(String) -> T,
	                             private val write: SharedPreferences.(String, T) -> Unit) : ReadWriteProperty<Settings, T>
	{
		override fun getValue(thisRef: Settings, property: KProperty<*>) =
			thisRef.sharedPreferences.read(property.name)

		override fun setValue(thisRef: Settings, property: KProperty<*>, value: T) =
			thisRef.sharedPreferences.write(property.name, value)
	}

	private lateinit var sharedPreferences: SharedPreferences

	val speechDuration by duration(7.minutes)
	val speechDurationMax by duration(7.minutes + 15.seconds)
	val poiStartEnabled by boolean(true)
	val poiStart by duration(1.minutes)
	val poiEndEnabled by boolean(true)
	val poiEnd by duration(6.minutes)

	fun init(context: Context)
	{
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
	}

	private fun boolean(defaultValue: Boolean) =
		PreferencesDelegate(read = { key -> getBoolean(key, defaultValue) },
		                    write = { key, value -> edit { putBoolean(key, value) } })

	private fun duration(defaultValue: Duration) =
		PreferencesDelegate(read = { key -> getInt(key, defaultValue.inMilliseconds.toInt()).milliseconds },
		                    write = { key, value -> edit { putInt(key, value.inMilliseconds.toInt()) } })
}
