package pl.karol202.bphelper.interactors.usecases.member

import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase1

class RemoveMemberUseCase(override val function: suspend (Long) -> Unit) : SuspendUseCase1<Long, Unit>

fun removeMemberUseCaseFactory(memberRepository: MemberRepository) = RemoveMemberUseCase { memberId ->
	memberRepository.removeMember(memberId)
}
