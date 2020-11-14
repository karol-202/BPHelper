package pl.karol202.bphelper.interactors.usecases.member.impl

import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.interactors.usecases.member.RemoveMemberUseCase

class RemoveMemberUseCaseImpl(private val memberRepository: MemberRepository) : RemoveMemberUseCase
{
	override suspend fun invoke(memberId: Long) = memberRepository.removeMember(memberId)
}
