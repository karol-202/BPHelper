package pl.karol202.bphelper.framework.extensions

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.checkSelfPermissionCompat(permission: String) = ContextCompat.checkSelfPermission(this, permission)
