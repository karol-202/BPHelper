package pl.karol202.bphelper.ui.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getColorCompat(@ColorRes res: Int) = ContextCompat.getColor(this, res)
