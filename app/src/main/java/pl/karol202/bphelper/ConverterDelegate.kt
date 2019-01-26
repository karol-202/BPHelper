package pl.karol202.bphelper

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T, I, O> ReadOnlyProperty<T, I>.convert(readConverter: (I) -> O): ReadOnlyProperty<T, O> =
	ReadConverterDelegate { thisRef, property ->
		readConverter(getValue(thisRef, property))
	}

fun <T, I, O> ReadWriteProperty<T, I>.convert(readConverter: (I) -> O): ReadOnlyProperty<T, O> =
	ReadConverterDelegate { thisRef, property ->
		readConverter(getValue(thisRef, property))
	}

fun <T, I, O> ReadWriteProperty<T, I>.convert(readConverter: (I) -> O, writeConverter: (O) -> I): ReadWriteProperty<T, O> =
	ReadWriteConverterDelegate(readProvider = { thisRef, property -> readConverter(getValue(thisRef, property)) },
	                           writeConsumer = { thisRef, property, value -> setValue(thisRef, property, writeConverter(value)) })


private class ReadConverterDelegate<T, O>(private val readProvider: (T, KProperty<*>) -> O) : ReadOnlyProperty<T, O>
{
	override operator fun getValue(thisRef: T, property: KProperty<*>) = readProvider(thisRef, property)
}

private class ReadWriteConverterDelegate<T, O>(private val readProvider: (T, KProperty<*>) -> O,
                                               private val writeConsumer: (T, KProperty<*>, O) -> Unit) : ReadWriteProperty<T, O>
{
	override operator fun getValue(thisRef: T, property: KProperty<*>) = readProvider(thisRef, property)

	override operator fun setValue(thisRef: T, property: KProperty<*>, value: O) = writeConsumer(thisRef, property, value)
}
