package pl.karol202.bphelper.interactors.usecases.recording

import pl.karol202.bphelper.domain.entity.PermissionType
import pl.karol202.bphelper.domain.service.PermissionService
import pl.karol202.bphelper.domain.service.RecordingService
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase1

class StartRecordingUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun startRecordingUseCaseFactory(recordingService: RecordingService,
                                 permissionService: PermissionService) = StartRecordingUseCase { recordingName ->
	permissionService.ensurePermissionGranted(PermissionType.RECORDING)
	// TODO Check whether granted
	recordingService.start(recordingName)
}

