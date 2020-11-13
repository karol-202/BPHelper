package pl.karol202.bphelper.data.datastore

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.entity.MemberEntity
import pl.karol202.bphelper.data.entity.NewMemberEntity

interface MemberDataStore
{
	val allMembers: Flow<List<MemberEntity>>

	suspend fun addMember(member: NewMemberEntity): MemberEntity

	suspend fun updateMember(member: MemberEntity)

	suspend fun removeMember(member: MemberEntity)
}
