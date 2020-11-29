package pl.karol202.bphelper.data.datastore

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.model.MemberModel
import pl.karol202.bphelper.data.model.NewMemberModel

interface MemberDataStore
{
	val allMembers: Flow<List<MemberModel>>

	suspend fun addMember(member: NewMemberModel): MemberModel

	suspend fun updateMember(member: MemberModel)

	suspend fun removeMember(memberId: Long)
}
