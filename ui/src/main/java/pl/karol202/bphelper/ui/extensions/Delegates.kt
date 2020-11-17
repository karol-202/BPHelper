package pl.karol202.bphelper.ui.extensions

import kotlin.properties.ReadOnlyProperty

infix fun <T, V, R> ReadOnlyProperty<T, V>.map(transform: (V) -> R) =
	ReadOnlyProperty<T, R> { thisRef, property -> transform(getValue(thisRef, property)) }
