package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.presentation.viewdata.PermissionRequestViewData

interface PermissionViewModel : ViewModel
{
	val permissionRequests: Flow<PermissionRequestViewData>

	fun markPermissionRequestProcessed(permission: PermissionRequestViewData.Type)
}
