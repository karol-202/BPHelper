package pl.karol202.bphelper.interactors.usecases.member

import pl.karol202.bphelper.domain.entity.Member
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase1

class UpdateMemberUseCase(override val function: suspend (Member) -> Unit) : SuspendUseCase1<Member, Unit>

fun updateMemberUseCaseFactory(memberRepository: MemberRepository) = UpdateMemberUseCase { member ->
	memberRepository.updateMember(member)
}
