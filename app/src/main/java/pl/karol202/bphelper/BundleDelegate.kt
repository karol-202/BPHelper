package pl.karol202.bphelper

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class BundleDelegate<T>(private val bundle: Bundle) : ReadWriteProperty<Any?, T>
{
	class Nullable<T : Any>(instanceState: Bundle) : BundleDelegate<T?>(instanceState)
	{
		override operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = getFromBundle(property.name)
	}

	class NotNull<T : Any>(instanceState: Bundle,
	                       private val defaultValueProvider: () -> T) : BundleDelegate<T>(instanceState)
	{
		override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getFromBundle(property.name) ?: defaultValueProvider().also {
			setValue(thisRef, property, it)
		}
	}

	protected fun getFromBundle(name: String) = bundle[name] as T?

	override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
	{
		val name = property.name
		when(value)
		{
			null -> bundle.remove(name)
			is Boolean -> bundle.putBoolean(name, value)
			is Byte -> bundle.putByte(name, value)
			is Char -> bundle.putChar(name, value)
			is Double -> bundle.putDouble(name, value)
			is Float -> bundle.putFloat(name, value)
			is Int -> bundle.putInt(name, value)
			is Long -> bundle.putLong(name, value)
			is Short -> bundle.putShort(name, value)
			is String -> bundle.putString(name, value)
			is Bundle -> bundle.putBundle(name, value)
			is Parcelable -> bundle.putParcelable(name, value)
			is Serializable -> bundle.putSerializable(name, value)
			else -> throw IllegalArgumentException("Type not supported.")
		}
	}
}
