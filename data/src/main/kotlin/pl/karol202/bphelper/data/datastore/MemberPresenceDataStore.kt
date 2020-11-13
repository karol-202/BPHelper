package pl.karol202.bphelper.data.datastore

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.entity.MemberEntity

interface MemberPresenceDataStore
{
	val membersPresences: Flow<Map<Long, MemberEntity.Presence>>

	suspend fun setMemberPresence(memberId: Long, memberPresence: MemberEntity.Presence)

	suspend fun removeMemberPresence(memberId: Long)
}
