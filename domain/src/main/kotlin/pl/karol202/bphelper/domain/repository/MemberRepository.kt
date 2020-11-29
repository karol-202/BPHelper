package pl.karol202.bphelper.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.entity.Member
import pl.karol202.bphelper.domain.entity.NewMember

interface MemberRepository
{
	val allMembers: Flow<List<Member>>

	suspend fun addMember(member: NewMember)

	suspend fun updateMember(member: Member)

	suspend fun removeMember(memberId: Long)
}
