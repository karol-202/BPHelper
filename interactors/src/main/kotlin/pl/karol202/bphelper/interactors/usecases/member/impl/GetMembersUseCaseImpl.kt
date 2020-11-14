package pl.karol202.bphelper.interactors.usecases.member.impl

import kotlinx.coroutines.flow.first
import pl.karol202.bphelper.interactors.usecases.member.GetMembersFlowUseCase
import pl.karol202.bphelper.interactors.usecases.member.GetMembersUseCase

class GetMembersUseCaseImpl(private val getMembersFlowUseCase: GetMembersFlowUseCase) : GetMembersUseCase
{
	override suspend fun invoke() = getMembersFlowUseCase().first()
}
