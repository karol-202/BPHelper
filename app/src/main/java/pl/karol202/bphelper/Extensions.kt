package pl.karol202.bphelper

import android.content.Context
import android.os.Build
import android.view.View
import org.jetbrains.anko.AnkoContext

val View.ctx: Context
	get() = context

fun AnkoContext.Companion.createFromView(view: View) = create(view.context, view)

inline fun <T> doOnApi(api: Int, block: () -> T)
{
	if(Build.VERSION.SDK_INT >= api) block()
}
