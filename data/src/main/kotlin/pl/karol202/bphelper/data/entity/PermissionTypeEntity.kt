package pl.karol202.bphelper.data.entity

import pl.karol202.bphelper.domain.model.PermissionType

enum class PermissionTypeEntity
{
	RECORDING
}

fun PermissionType.toEntity() = when(this)
{
	PermissionType.RECORDING -> PermissionTypeEntity.RECORDING
}
