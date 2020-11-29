package pl.karol202.bphelper.data.controller

import pl.karol202.bphelper.data.model.PermissionTypeModel

interface PermissionController
{
	fun isPermissionGranted(permission: PermissionTypeModel): Boolean
}
