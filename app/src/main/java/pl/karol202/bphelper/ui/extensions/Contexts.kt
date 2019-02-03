package pl.karol202.bphelper.ui.extensions

import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

inline fun <T> doOnApi(api: Int, block: () -> T) = if(Build.VERSION.SDK_INT >= api) block() else null

fun Context.getDrawableCompat(@DrawableRes res: Int) =
	ContextCompat.getDrawable(this, res) ?: throw Exception("Could not load drawable")
