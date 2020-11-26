package pl.karol202.bphelper.interactors.usecases.permission.impl

import pl.karol202.bphelper.domain.service.PermissionService
import pl.karol202.bphelper.interactors.usecases.permission.GetPermissionRequestFlowUseCase

class GetPermissionRequestFlowUseCaseImpl(private val permissionService: PermissionService) : GetPermissionRequestFlowUseCase
{
	override fun invoke() = permissionService.permissionRequests
}
