package pl.karol202.bphelper.domain.service

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.PermissionType

interface PermissionService
{
	val permissionRequests: Flow<PermissionType>

	fun markPermissionRequestProcessed(permission: PermissionType)

	suspend fun ensurePermissionGranted(permission: PermissionType): Boolean
}
