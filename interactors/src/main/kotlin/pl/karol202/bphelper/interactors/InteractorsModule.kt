package pl.karol202.bphelper.interactors

import org.koin.dsl.module
import pl.karol202.bphelper.interactors.usecases.member.*
import pl.karol202.bphelper.interactors.usecases.member.impl.*
import pl.karol202.bphelper.interactors.usecases.settings.GetSettingsUseCase
import pl.karol202.bphelper.interactors.usecases.settings.impl.GetSettingsUseCaseImpl
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

	single<DrawTableConfigurationUseCase> { DrawTableConfigurationUseCaseImpl(get(), get(), get()) }
	single<CheckIfTableConfigurationPossibleUseCase> { CheckIfTableConfigurationPossibleUseCaseImpl(get(), get(), get()) }
}