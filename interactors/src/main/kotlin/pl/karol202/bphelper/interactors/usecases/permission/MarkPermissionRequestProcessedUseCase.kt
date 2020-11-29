package pl.karol202.bphelper.interactors.usecases.permission

import pl.karol202.bphelper.domain.entity.PermissionType

interface MarkPermissionRequestProcessedUseCase
{
	operator fun invoke(permission: PermissionType)
}
