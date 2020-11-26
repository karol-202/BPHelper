package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.interactors.usecases.permission.GetPermissionRequestFlowUseCase
import pl.karol202.bphelper.interactors.usecases.permission.MarkPermissionRequestProcessedUseCase
import pl.karol202.bphelper.presentation.viewdata.*
import pl.karol202.bphelper.presentation.viewmodel.PermissionViewModel

class PermissionViewModelImpl(getPermissionRequestFlowUseCase: GetPermissionRequestFlowUseCase,
                              private val markPermissionRequestProcessedUseCase: MarkPermissionRequestProcessedUseCase) :
	BaseViewModel(), PermissionViewModel
{
	override val permissionRequests = getPermissionRequestFlowUseCase().map { type ->
		PermissionRequestViewData(type.toViewDataType(), getNextRequestId())
	}

	private var requestCounter = 0

	private fun getNextRequestId() = requestCounter++

	override fun markPermissionRequestProcessed(permission: PermissionRequestViewData.Type) = launch {
		markPermissionRequestProcessedUseCase(permission.toModel())
	}
}
