package pl.karol202.bphelper.data.datastore

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.model.MemberModel

interface MemberPresenceDataStore
{
	val membersPresences: Flow<Map<Long, MemberModel.Presence>>

	suspend fun setMemberPresence(memberId: Long, memberPresence: MemberModel.Presence)

	suspend fun removeMemberPresence(memberId: Long)
}
