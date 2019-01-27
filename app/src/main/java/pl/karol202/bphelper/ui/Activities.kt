package pl.karol202.bphelper.ui

import android.app.Activity
import android.content.Context
import org.jetbrains.anko.startActivity
import kotlin.reflect.KProperty

data class ActivityParameter<T>(val property: KProperty<T>,
                                val value: T?)
{
	fun toStringValuePair() = property.name to value
}

infix fun <T> KProperty<T>.to(value: T?) = ActivityParameter(this, value)

inline fun <reified A: Activity> Context.startActivity(vararg params: ActivityParameter<*>) =
	startActivity<A>(*params.map { it.toStringValuePair() }.toTypedArray())