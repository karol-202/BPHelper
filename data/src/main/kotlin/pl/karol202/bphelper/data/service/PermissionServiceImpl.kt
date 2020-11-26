package pl.karol202.bphelper.data.service

import kotlinx.coroutines.flow.*
import pl.karol202.bphelper.data.controller.PermissionController
import pl.karol202.bphelper.data.entity.toEntity
import pl.karol202.bphelper.domain.model.PermissionType
import pl.karol202.bphelper.domain.service.PermissionService

class PermissionServiceImpl(private val permissionController: PermissionController) : PermissionService
{
	private val pendingRequests = MutableStateFlow(emptySet<PermissionType>())

	override val permissionRequests = pendingRequests
		.scan(emptySet<PermissionType>() to emptySet<PermissionType>()) { (lastPendingRequests, _), pendingRequests ->
			pendingRequests to pendingRequests - lastPendingRequests
		}.flatMapConcat { (_, newRequests) -> newRequests.asFlow() }

	override fun markPermissionRequestProcessed(permission: PermissionType)
	{
		pendingRequests.value -= permission
	}

	override suspend fun ensurePermissionGranted(permission: PermissionType): Boolean
	{
		if(isPermissionGranted(permission)) return true
		if(permission !in pendingRequests.value) pendingRequests.value += permission
		pendingRequests.takeWhile { permission in it }.collect()
		return isPermissionGranted(permission)
	}

	private fun isPermissionGranted(permission: PermissionType) =
		permissionController.isPermissionGranted(permission.toEntity())
}
