package pl.karol202.bphelper.interactors

import org.koin.dsl.module
import pl.karol202.bphelper.interactors.usecases.member.AddMemberUseCase
import pl.karol202.bphelper.interactors.usecases.member.GetMembersUseCase
import pl.karol202.bphelper.interactors.usecases.member.RemoveMemberUseCase
import pl.karol202.bphelper.interactors.usecases.member.UpdateMemberUseCase
import pl.karol202.bphelper.interactors.usecases.member.impl.AddMemberUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.member.impl.GetMembersUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.member.impl.RemoveMemberUseCaseImpl
import pl.karol202.bphelper.interactors.usecases.member.impl.UpdateMemberUseCaseImpl

fun interactorsModule() = module {
	single<GetMembersUseCase> { GetMembersUseCaseImpl(get()) }
	single<AddMemberUseCase> { AddMemberUseCaseImpl(get()) }
	single<UpdateMemberUseCase> { UpdateMemberUseCaseImpl(get()) }
	single<RemoveMemberUseCase> { RemoveMemberUseCaseImpl(get()) }
}
