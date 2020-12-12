package pl.karol202.bphelper.interactors.usecases.member

import pl.karol202.bphelper.domain.entity.Member
import pl.karol202.bphelper.domain.entity.NewMember
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase1

class AddMemberUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun addMemberUseCaseFactory(memberRepository: MemberRepository) = AddMemberUseCase { name ->
	memberRepository.addMember(NewMember(name, Member.Presence.NONE))
}
