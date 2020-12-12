package pl.karol202.bphelper.interactors.usecases.permission

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.PermissionType
import pl.karol202.bphelper.domain.service.PermissionService
import pl.karol202.bphelper.interactors.usecases.UseCase0

class GetPermissionRequestFlowUseCase(override val function: () -> Flow<PermissionType>) : UseCase0<Flow<PermissionType>>

fun getPermissionRequestFlowUseCaseFactory(permissionService: PermissionService) = GetPermissionRequestFlowUseCase {
	permissionService.permissionRequests
}
