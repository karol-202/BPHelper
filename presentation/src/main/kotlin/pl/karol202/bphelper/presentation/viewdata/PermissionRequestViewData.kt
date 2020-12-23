package pl.karol202.bphelper.presentation.viewdata

import pl.karol202.bphelper.domain.entity.PermissionType

data class PermissionRequestViewData(val type: Type,
                                     val id: Int)
{
	enum class Type
	{
		RECORDING;

		companion object
	}
}

fun PermissionType.toViewDataType() = when(this)
{
	PermissionType.RECORDING -> PermissionRequestViewData.Type.RECORDING
}

fun PermissionRequestViewData.Type.toEntity() = when(this)
{
	PermissionRequestViewData.Type.RECORDING -> PermissionType.RECORDING
}