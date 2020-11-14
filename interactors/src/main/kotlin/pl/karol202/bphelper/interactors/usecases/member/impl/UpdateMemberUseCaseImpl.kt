package pl.karol202.bphelper.interactors.usecases.member.impl

import pl.karol202.bphelper.domain.model.Member
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.interactors.usecases.member.UpdateMemberUseCase

class UpdateMemberUseCaseImpl(private val memberRepository: MemberRepository) : UpdateMemberUseCase
{
	override suspend fun invoke(member: Member) = memberRepository.updateMember(member)
}
