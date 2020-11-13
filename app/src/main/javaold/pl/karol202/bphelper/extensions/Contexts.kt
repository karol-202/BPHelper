package pl.karol202.bphelper.extensions

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

inline fun <T> doOnApi(api: Int, block: () -> T) = if(Build.VERSION.SDK_INT >= api) block() else null

inline fun <T> doOnApi(api: Int, block: () -> T, fallback: () -> T) = if(Build.VERSION.SDK_INT >= api) block() else fallback()

fun Context.getColorCompat(@ColorRes res: Int) = ContextCompat.getColor(this, res)
