package pl.karol202.bphelper

import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View
import org.jetbrains.anko.AnkoContext

val View.ctx: Context
	get() = context

fun AnkoContext.Companion.createFromView(view: View) = create(view.context, view)

fun Context.getDrawableCompat(@DrawableRes res: Int) = ContextCompat.getDrawable(this, res) ?:
		throw Exception("Could not load drawable")

inline fun <T> doOnApi(api: Int, block: () -> T)
{
	if(Build.VERSION.SDK_INT >= api) block()
}
