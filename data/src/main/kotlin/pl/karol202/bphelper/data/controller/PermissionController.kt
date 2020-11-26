package pl.karol202.bphelper.data.controller

import pl.karol202.bphelper.data.entity.PermissionTypeEntity

interface PermissionController
{
	fun isPermissionGranted(permission: PermissionTypeEntity): Boolean
}
