package pl.karol202.bphelper.framework.controller

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import pl.karol202.bphelper.data.controller.PermissionController
import pl.karol202.bphelper.data.model.PermissionTypeModel
import pl.karol202.bphelper.framework.extensions.checkSelfPermissionCompat

class PermissionControllerImpl(private val context: Context) : PermissionController
{
	override fun isPermissionGranted(permission: PermissionTypeModel) =
		context.checkSelfPermissionCompat(permission.androidName) == PackageManager.PERMISSION_GRANTED

	private val PermissionTypeModel.androidName get() = when(this)
	{
		PermissionTypeModel.RECORDING -> Manifest.permission.RECORD_AUDIO
	}
}
