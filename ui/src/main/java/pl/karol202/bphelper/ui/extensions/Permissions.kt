package pl.karol202.bphelper.ui.extensions

import android.Manifest
import pl.karol202.bphelper.presentation.viewdata.PermissionRequestViewData

val PermissionRequestViewData.Type.androidName get() = when(this)
{
	PermissionRequestViewData.Type.RECORDING -> Manifest.permission.RECORD_AUDIO
}

fun PermissionRequestViewData.Type.Companion.findByAndroidName(name: String) = when(name)
{
	Manifest.permission.RECORD_AUDIO -> PermissionRequestViewData.Type.RECORDING
	else -> throw IllegalArgumentException("Unknown permission: $name")
}
