package pl.karol202.bphelper.data.datastore.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.karol202.bphelper.data.datastore.MemberPresenceDataStore
import pl.karol202.bphelper.data.entity.MemberEntity

class InMemoryMemberPresenceDataStore : MemberPresenceDataStore
{
	private val state = MutableStateFlow(emptyMap<Long, MemberEntity.Presence>())

	override val membersPresences: Flow<Map<Long, MemberEntity.Presence>> = state

	override suspend fun setMemberPresence(memberId: Long, memberPresence: MemberEntity.Presence)
	{
		state.value += memberId to memberPresence
	}

	override suspend fun removeMemberPresence(memberId: Long)
	{
		state.value -= memberId
	}
}
