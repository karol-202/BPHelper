package pl.karol202.bphelper

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class IntentDelegate<T> : ReadWriteProperty<Activity, T>
{
	class Nullable<T : Any> : IntentDelegate<T?>()
	{
		override operator fun getValue(thisRef: Activity, property: KProperty<*>): T? = thisRef.getFromIntent(property.name)
	}

	class NotNull<T : Any>(private val defaultValueProvider: () -> T) : IntentDelegate<T>()
	{
		override operator fun getValue(thisRef: Activity, property: KProperty<*>): T =
			thisRef.getFromIntent(property.name) ?: defaultValueProvider().also {
				setValue(thisRef, property, it)
			}
	}

	protected fun Activity.getFromIntent(name: String) = intent?.extras?.get(name) as T?

	override operator fun setValue(thisRef: Activity, property: KProperty<*>, value: T)
	{
		val intent = thisRef.intent
		val name = property.name
		when(value)
		{
			null -> intent.removeExtra(name)
			is Boolean -> intent.putExtra(name, value)
			is Byte -> intent.putExtra(name, value)
			is Char -> intent.putExtra(name, value)
			is Double -> intent.putExtra(name, value)
			is Float -> intent.putExtra(name, value)
			is Int -> intent.putExtra(name, value)
			is Long -> intent.putExtra(name, value)
			is Short -> intent.putExtra(name, value)
			is String -> intent.putExtra(name, value)
			is Bundle -> intent.putExtra(name, value)
			is Parcelable -> intent.putExtra(name, value)
			is Serializable -> intent.putExtra(name, value)
			is Array<*> -> when
			{
				value.isArrayOf<CharSequence>() -> intent.putExtra(name, value)
				value.isArrayOf<String>() -> intent.putExtra(name, value)
				value.isArrayOf<Parcelable>() -> intent.putExtra(name, value)
			}
			else -> throw IllegalArgumentException("Type not supported.")
		}
	}
}
