package pl.karol202.bphelper

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.startActivity
import kotlin.reflect.KProperty

val View.ctx: Context
	get() = context

fun AnkoContext.Companion.createFromView(view: View) = create(view.context, view)

fun Context.getDrawableCompat(@DrawableRes res: Int) = ContextCompat.getDrawable(this, res) ?:
		throw Exception("Could not load drawable")

inline fun <T> doOnApi(api: Int, block: () -> T)
{
	if(Build.VERSION.SDK_INT >= api) block()
}


data class ActivityParameter<T>(val property: KProperty<T>,
                                val value: T?)
{
	fun toStringValuePair() = property.name to value
}

infix fun <T> KProperty<T>.to(value: T?) = ActivityParameter(this, value)

inline fun <reified A: Activity> Context.startActivity(vararg params: ActivityParameter<*>) =
		startActivity<A>(*params.map { it.toStringValuePair() }.toTypedArray())
