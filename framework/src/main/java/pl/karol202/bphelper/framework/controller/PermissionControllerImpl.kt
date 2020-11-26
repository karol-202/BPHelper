package pl.karol202.bphelper.framework.controller

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import pl.karol202.bphelper.data.controller.PermissionController
import pl.karol202.bphelper.data.entity.PermissionTypeEntity
import pl.karol202.bphelper.framework.extensions.checkSelfPermissionCompat

class PermissionControllerImpl(private val context: Context) : PermissionController
{
	override fun isPermissionGranted(permission: PermissionTypeEntity) =
		context.checkSelfPermissionCompat(permission.androidName) == PackageManager.PERMISSION_GRANTED

	private val PermissionTypeEntity.androidName get() = when(this)
	{
		PermissionTypeEntity.RECORDING -> Manifest.permission.RECORD_AUDIO
	}
}
