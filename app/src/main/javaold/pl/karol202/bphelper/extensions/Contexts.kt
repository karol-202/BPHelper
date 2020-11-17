package pl.karol202.bphelper.extensions

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getColorCompat(@ColorRes res: Int) = ContextCompat.getColor(this, res)
