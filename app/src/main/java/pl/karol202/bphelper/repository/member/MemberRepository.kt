package pl.karol202.bphelper.repository.member

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.model.Member

interface MemberRepository
{
	val allMembers: Flow<List<Member>>

	fun addMember(member: Member)

	fun updateMember(member: Member)

	fun removeMember(member: Member)
}
