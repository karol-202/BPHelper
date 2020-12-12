package pl.karol202.bphelper.interactors.usecases.permission

import pl.karol202.bphelper.domain.entity.PermissionType
import pl.karol202.bphelper.domain.service.PermissionService
import pl.karol202.bphelper.interactors.usecases.UseCase1

class MarkPermissionRequestProcessedUseCase(override val function: (PermissionType) -> Unit) : UseCase1<PermissionType, Unit>

fun markPermissionRequestProcessedUseCaseFactory(permissionService: PermissionService) =
	MarkPermissionRequestProcessedUseCase { permission ->
		permissionService.markPermissionRequestProcessed(permission)
	}
