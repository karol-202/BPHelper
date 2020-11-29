package pl.karol202.bphelper.interactors.usecases.permission

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.PermissionType

interface GetPermissionRequestFlowUseCase
{
	operator fun invoke(): Flow<PermissionType>
}
