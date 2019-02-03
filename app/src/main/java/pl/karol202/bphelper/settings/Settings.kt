package pl.karol202.bphelper.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import pl.karol202.bphelper.Duration
import pl.karol202.bphelper.orThrow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object Settings
{
	class PreferencesDelegate<T>(private val read: SharedPreferences.(String) -> T,
	                             private val write: SharedPreferences.(String, T) -> Unit) : ReadWriteProperty<Settings, T>
	{
		override fun getValue(thisRef: Settings, property: KProperty<*>) = thisRef.sharedPreferences.read(property.name)

		override fun setValue(thisRef: Settings, property: KProperty<*>, value: T) = thisRef.sharedPreferences.write(property.name, value)
	}

	private lateinit var sharedPreferences: SharedPreferences

	val speechDuration by duration(Duration.create(minutes = 7).orThrow())
	val speechDurationMax by duration(Duration.create(minutes = 7, seconds = 15).orThrow())
	val poiStartEnabled by boolean(true)
	val poiStart by duration(Duration.create(minutes = 1).orThrow())
	val poiEndEnabled by boolean(true)
	val poiEnd by duration(Duration.create(minutes = 6).orThrow())

	fun init(context: Context)
	{
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
	}

	private fun boolean(defaultValue: Boolean) =
		PreferencesDelegate(read = { key -> getBoolean(key, defaultValue) },
		                    write = { key, value -> edit { putBoolean(key, value) } })

	private fun duration(defaultValue: Duration) =
		PreferencesDelegate(read = { key -> Duration.fromMillis(getInt(key, defaultValue.timeInMillis)).orThrow() },
		                    write = { key, value -> edit { putInt(key, value.timeInMillis) } })
}