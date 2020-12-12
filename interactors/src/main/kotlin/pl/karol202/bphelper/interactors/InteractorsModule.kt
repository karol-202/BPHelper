package pl.karol202.bphelper.interactors

import org.koin.dsl.module
import pl.karol202.bphelper.interactors.usecases.debatetimer.*
import pl.karol202.bphelper.interactors.usecases.member.*
import pl.karol202.bphelper.interactors.usecases.member.impl.*
import pl.karol202.bphelper.interactors.usecases.notification.showPrepTimerFinishNotificationUseCaseFactory
import pl.karol202.bphelper.interactors.usecases.permission.getPermissionRequestFlowUseCaseFactory
import pl.karol202.bphelper.interactors.usecases.permission.markPermissionRequestProcessedUseCaseFactory
import pl.karol202.bphelper.interactors.usecases.preptimer.*
import pl.karol202.bphelper.interactors.usecases.preptimer.impl.*
import pl.karol202.bphelper.interactors.usecases.recording.*
import pl.karol202.bphelper.interactors.usecases.recording.impl.*
import pl.karol202.bphelper.interactors.usecases.settings.getSettingsUseCaseFactory
import pl.karol202.bphelper.interactors.usecases.settings.updateSettingsUseCaseFactory
import pl.karol202.bphelper.interactors.usecases.sound.playSoundUseCaseFactory
import pl.karol202.bphelper.interactors.usecases.table.CheckIfTableConfigurationPossibleUseCase
import pl.karol202.bphelper.interactors.usecases.table.DrawTableConfigurationUseCase
import pl.karol202.bphelper.interactors.usecases.table.checkIfTableConfigurationPossibleUseCaseFactory
import pl.karol202.bphelper.interactors.usecases.table.drawTableConfigurationUseCaseFactory
import pl.karol202.bphelper.interactors.usecases.table.impl.DrawTableConfigurationUseCaseImpl

fun interactorsModule() = module {
	single { getMembersUseCaseFactory(get()) }
	single { getMembersFlowUseCaseFactory(get()) }
	single { addMemberUseCaseFactory(get()) }
	single { updateMemberUseCaseFactory(get()) }
	single { removeMemberUseCaseFactory(get()) }

	single { getSettingsUseCaseFactory(get()) }
	single { updateSettingsUseCaseFactory(get()) }

	single { drawTableConfigurationUseCaseFactory(get(), get(), get()) }
	single { checkIfTableConfigurationPossibleUseCaseFactory(get(), get(), get()) }

	single { getPrepTimerDurationFlowUseCaseFactory(get()) }
	single { getPrepTimerActiveFlowUseCaseFactory(get()) }
	single { getPrepTimerFinishNotificationEventFlowUseCaseFactory(get()) }
	single { setPrepTimerDurationUseCaseFactory(get()) }
	single { startPrepTimerUseCaseFactory(get()) }
	single { stopPrepTimerUseCaseFactory(get()) }

	single { showPrepTimerFinishNotificationUseCaseFactory(get()) }

	single { getDebateTimerValueFlowUseCaseFactory(get()) }
	single { getDebateTimerActiveFlowUseCaseFactory(get()) }
	single { getDebateTimerOvertimeFlowUseCaseFactory(get()) }
	single { getDebateTimerOvertimeBellEventFlowUseCaseFactory(get()) }
	single { getDebateTimerPoiFlowUseCaseFactory(get()) }
	single { getDebateTimerPoiBellEventFlowUseCaseFactory(get()) }
	single { startDebateTimerUseCaseFactory(get()) }
	single { pauseDebateTimerUseCaseFactory(get()) }
	single { resetDebateTimerUseCaseFactory(get()) }

	single { playSoundUseCaseFactory(get()) }

	single { getRecordingFlowUseCaseFactory(get()) }
	single { getRecordingEventFlowUseCaseFactory(get()) }
	single { startRecordingUseCaseFactory(get()) }
	single { stopRecordingUseCaseFactory(get()) }
	single { isRecordingNameAvailableUseCaseFactory(get()) }

	single { markPermissionRequestProcessedUseCaseFactory(get()) }
	single { getPermissionRequestFlowUseCaseFactory(get()) }
}
