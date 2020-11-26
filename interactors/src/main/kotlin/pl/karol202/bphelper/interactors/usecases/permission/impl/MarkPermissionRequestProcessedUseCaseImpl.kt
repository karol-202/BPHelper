package pl.karol202.bphelper.interactors.usecases.permission.impl

import pl.karol202.bphelper.domain.model.PermissionType
import pl.karol202.bphelper.domain.service.PermissionService
import pl.karol202.bphelper.interactors.usecases.permission.MarkPermissionRequestProcessedUseCase

class MarkPermissionRequestProcessedUseCaseImpl(private val permissionService: PermissionService) :
	MarkPermissionRequestProcessedUseCase
{
	override fun invoke(permission: PermissionType) =
		permissionService.markPermissionRequestProcessed(permission)
}
