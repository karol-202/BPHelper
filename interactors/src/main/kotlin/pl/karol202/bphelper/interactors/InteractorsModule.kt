package pl.karol202.bphelper.interactors

import org.koin.dsl.module
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerActiveFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerOvertimeBellEventFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerOvertimeFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerPoiBellEventFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerPoiFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.GetDebateTimerValueFlowUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.PauseDebateTimerUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.ResetDebateTimerUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.StartDebateTimerUseCase
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.GetDebateTimerActiveFlowUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.GetDebateTimerOvertimeBellEventFlowUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.GetDebateTimerOvertimeFlowUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.GetDebateTimerPoiBellEventFlowUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.GetDebateTimerPoiFlowUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.GetDebateTimerValueFlowUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.PauseDebateTimerUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.ResetDebateTimerUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.debatetimer.impl.StartDebateTimerUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.member.*
import pl.karol202.bphelper.interactors.usecases.member.impl.*
import pl.karol202.bphelper.interactors.usecases.notification.ShowPrepTimerFinishNotificationUseCase
import pl.karol202.bphelper.interactors.usecases.notification.impl.ShowPrepTimerFinishNotificationUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.permission.GetPermissionRequestFlowUseCase
import pl.karol202.bphelper.interactors.usecases.permission.MarkPermissionRequestProcessedUseCase
import pl.karol202.bphelper.interactors.usecases.permission.impl.GetPermissionRequestFlowUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.permission.impl.MarkPermissionRequestProcessedUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.preptimer.*
import pl.karol202.bphelper.interactors.usecases.preptimer.impl.*
import pl.karol202.bphelper.interactors.usecases.recording.*
import pl.karol202.bphelper.interactors.usecases.recording.impl.*
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsFlowUseCase
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase
import pl.karol202.bphelper.interactors.usecases.settings.impl.GetSettingsFlowUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.settings.impl.GetSettingsUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.sound.PlaySoundUseCase
import pl.karol202.bphelper.interactors.usecases.sound.impl.PlaySoundUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.table.CheckIfTableConfigurationPossibleUseCase
import pl.karol202.bphelper.interactors.usecases.table.DrawTableConfigurationUseCase
import pl.karol202.bphelper.interactors.usecases.table.impl.CheckIfTableConfigurationPossibleUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.table.impl.DrawTableConfigurationUseCaseImpl

fun interactorsModule() = module {
	single<GetMembersUseCase> { GetMembersUseCaseImpl(get()) }
	single<GetMembersFlowUseCase> { GetMembersFlowUseCaseImpl(get()) }
	single<AddMemberUseCase> { AddMemberUseCaseImpl(get()) }
	single<UpdateMemberUseCase> { UpdateMemberUseCaseImpl(get()) }
	single<RemoveMemberUseCase> { RemoveMemberUseCaseImpl(get()) }

	single<GetSettingsUseCase> { GetSettingsUseCaseImpl(get()) }
	single<GetSettingsFlowUseCase> { GetSettingsFlowUseCaseImpl(get()) }

	single<DrawTableConfigurationUseCase> { DrawTableConfigurationUseCaseImpl(get(), get(), get()) }
	single<CheckIfTableConfigurationPossibleUseCase> { CheckIfTableConfigurationPossibleUseCaseImpl(get(), get(), get()) }

	single<GetPrepTimerDurationFlowUseCase> { GetPrepTimerDurationFlowUseCaseImpl(get()) }
	single<GetPrepTimerActiveFlowUseCase> { GetPrepTimerActiveFlowUseCaseImpl(get()) }
	single<GetPrepTimerFinishNotificationEventFlowUseCase> { GetPrepTimerFinishNotificationEventFlowUseCaseImpl(get()) }
	single<SetPrepTimerDurationUseCase> { SetPrepTimerDurationUseCaseImpl(get()) }
	single<StartPrepTimerUseCase> { StartPrepTimerUseCaseImpl(get()) }
	single<StopPrepTimerUseCase> { StopPrepTimerUseCaseImpl(get()) }

	single<ShowPrepTimerFinishNotificationUseCase> { ShowPrepTimerFinishNotificationUseCaseImpl(get()) }

	single<GetDebateTimerValueFlowUseCase> { GetDebateTimerValueFlowUseCaseImpl(get()) }
	single<GetDebateTimerActiveFlowUseCase> { GetDebateTimerActiveFlowUseCaseImpl(get()) }
	single<GetDebateTimerOvertimeFlowUseCase> { GetDebateTimerOvertimeFlowUseCaseImpl(get()) }
	single<GetDebateTimerOvertimeBellEventFlowUseCase> { GetDebateTimerOvertimeBellEventFlowUseCaseImpl(get()) }
	single<GetDebateTimerPoiFlowUseCase> { GetDebateTimerPoiFlowUseCaseImpl(get()) }
	single<GetDebateTimerPoiBellEventFlowUseCase> { GetDebateTimerPoiBellEventFlowUseCaseImpl(get()) }
	single<StartDebateTimerUseCase> { StartDebateTimerUseCaseImpl(get()) }
	single<PauseDebateTimerUseCase> { PauseDebateTimerUseCaseImpl(get()) }
	single<ResetDebateTimerUseCase> { ResetDebateTimerUseCaseImpl(get()) }

	single<PlaySoundUseCase> { PlaySoundUseCaseImpl(get()) }

	single<GetRecordingFlowUseCase> { GetRecordingFlowUseCaseImpl(get()) }
	single<GetRecordingEventFlowUseCase> { GetRecordingEventFlowUseCaseImpl(get()) }
	single<StartRecordingUseCase> { StartRecordingUseCaseImpl(get()) }
	single<StopRecordingUseCase> { StopRecordingUseCaseImpl(get()) }
	single<IsRecordingNameAvailableUseCase> { IsRecordingNameAvailableUseCaseImpl(get()) }

	single<MarkPermissionRequestProcessedUseCase> { MarkPermissionRequestProcessedUseCaseImpl(get()) }
	single<GetPermissionRequestFlowUseCase> { GetPermissionRequestFlowUseCaseImpl(get()) }
}
