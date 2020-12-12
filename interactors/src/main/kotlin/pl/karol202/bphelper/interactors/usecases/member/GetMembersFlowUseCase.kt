package pl.karol202.bphelper.interactors.usecases.member

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.Member
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.interactors.usecases.UseCase0

class GetMembersFlowUseCase(override val function: () -> Flow<List<Member>>) : UseCase0<Flow<List<Member>>>

fun getMembersFlowUseCaseFactory(membersRepository: MemberRepository) = GetMembersFlowUseCase {
	membersRepository.allMembers
}
