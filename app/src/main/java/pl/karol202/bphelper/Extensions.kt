package pl.karol202.bphelper

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import org.jetbrains.anko.startActivity
import kotlin.reflect.KProperty

val View.ctx: Context
	get() = context

fun Context.getDrawableCompat(@DrawableRes res: Int) = ContextCompat.getDrawable(this, res) ?:
	throw Exception("Could not load drawable")


data class ActivityParameter<T>(val property: KProperty<T>,
                                val value: T?)
{
	fun toStringValuePair() = property.name to value
}

infix fun <T> KProperty<T>.to(value: T?) = ActivityParameter(this, value)

inline fun <reified A: Activity> Context.startActivity(vararg params: ActivityParameter<*>) =
		startActivity<A>(*params.map { it.toStringValuePair() }.toTypedArray())


fun Context.alertDialog(init: AlertDialog.Builder.() -> Unit) = AlertDialog.Builder(this).apply(init)
