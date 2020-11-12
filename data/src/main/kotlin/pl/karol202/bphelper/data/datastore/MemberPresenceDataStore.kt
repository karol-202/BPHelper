package pl.karol202.bphelper.data.datastore

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.entity.MemberPresenceEntity

interface MemberPresenceDataStore
{
	val membersPresences: Flow<Map<Int, MemberPresenceEntity>>

	suspend fun setMemberPresence(memberId: Int, memberPresence: MemberPresenceEntity)

	suspend fun removeMemberPresence(memberId: Int)
}
