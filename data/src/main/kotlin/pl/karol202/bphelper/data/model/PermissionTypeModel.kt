package pl.karol202.bphelper.data.model

import pl.karol202.bphelper.domain.entity.PermissionType

enum class PermissionTypeModel
{
	RECORDING
}

fun PermissionType.toModel() = when(this)
{
	PermissionType.RECORDING -> PermissionTypeModel.RECORDING
}
