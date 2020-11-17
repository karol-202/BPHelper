package pl.karol202.bphelper.framework.extensions

import android.os.Build

inline fun <T> doOnApi(api: Int, block: () -> T) = if(Build.VERSION.SDK_INT >= api) block() else null
