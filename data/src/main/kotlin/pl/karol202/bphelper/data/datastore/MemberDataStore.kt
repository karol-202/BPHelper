package pl.karol202.bphelper.data.datastore

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.entity.MemberEntity

interface MemberDataStore
{
	val allMembers: Flow<List<MemberEntity>>

	suspend fun addMember(member: MemberEntity)

	suspend fun updateMember(member: MemberEntity)

	suspend fun removeMember(member: MemberEntity)
}
