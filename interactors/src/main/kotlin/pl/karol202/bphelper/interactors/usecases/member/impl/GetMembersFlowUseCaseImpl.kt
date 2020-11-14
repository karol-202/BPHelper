package pl.karol202.bphelper.interactors.usecases.member.impl

import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.interactors.usecases.member.GetMembersFlowUseCase

class GetMembersFlowUseCaseImpl(private val membersRepository: MemberRepository) : GetMembersFlowUseCase
{
	override fun invoke() = membersRepository.allMembers
}
