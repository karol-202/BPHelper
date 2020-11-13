package pl.karol202.bphelper.domain.repository

import pl.karol202.bphelper.domain.model.Member
import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.model.NewMember

interface MemberRepository
{
	val allMembers: Flow<List<Member>>

	suspend fun addMember(member: NewMember)

	suspend fun updateMember(member: Member)

	suspend fun removeMember(member: Member)
}
