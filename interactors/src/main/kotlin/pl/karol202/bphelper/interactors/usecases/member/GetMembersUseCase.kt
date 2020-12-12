package pl.karol202.bphelper.interactors.usecases.member

import kotlinx.coroutines.flow.first
import pl.karol202.bphelper.domain.entity.Member
import pl.karol202.bphelper.interactors.usecases.SuspendUseCase0

class GetMembersUseCase(override val function: suspend () -> List<Member>) : SuspendUseCase0<List<Member>>

fun getMembersUseCaseFactory(getMembersFlowUseCase: GetMembersFlowUseCase) = GetMembersUseCase {
	getMembersFlowUseCase().first()
}
