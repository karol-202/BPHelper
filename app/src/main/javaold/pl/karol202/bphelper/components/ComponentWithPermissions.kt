package pl.karol202.bphelper.components

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

interface ComponentWithPermissions
{
	val ctx: Context

	fun checkPermission(permission: String) =
			ContextCompat.checkSelfPermission(ctx, permission) == PackageManager.PERMISSION_GRANTED

	fun requestPermission(permission: String, listener: (Boolean) -> Unit)
}