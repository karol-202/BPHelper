package pl.karol202.bphelper.interactors.usecases.member.impl

import pl.karol202.bphelper.domain.entity.Member
import pl.karol202.bphelper.domain.entity.NewMember
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.interactors.usecases.member.AddMemberUseCase

class AddMemberUseCaseImpl(private val memberRepository: MemberRepository) : AddMemberUseCase
{
	override suspend fun invoke(name: String) = memberRepository.addMember(NewMember(name, Member.Presence.NONE))
}
