package pl.karol202.bphelper.ui

import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

val View.ctx: Context
	get() = context

fun Context.getDrawableCompat(@DrawableRes res: Int) = ContextCompat.getDrawable(this, res) ?:
throw Exception("Could not load drawable")