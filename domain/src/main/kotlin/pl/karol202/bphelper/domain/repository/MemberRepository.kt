package pl.karol202.bphelper.domain.repository

import pl.karol202.bphelper.domain.model.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository
{
	val allMembers: Flow<List<Member>>

	suspend fun addMember(member: Member)

	suspend fun updateMember(member: Member)

	suspend fun removeMember(member: Member)
}
