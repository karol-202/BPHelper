package pl.karol202.bphelper.interactors.usecases.member

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.Member

interface GetMembersFlowUseCase
{
	operator fun invoke(): Flow<List<Member>>
}
