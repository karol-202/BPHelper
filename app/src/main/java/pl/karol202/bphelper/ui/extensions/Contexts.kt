package pl.karol202.bphelper.ui.extensions

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.getDrawableCompat(@DrawableRes res: Int) =
	ContextCompat.getDrawable(this, res) ?: throw Exception("Could not load drawable")