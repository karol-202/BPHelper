package pl.karol202.bphelper.data.datastore.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.karol202.bphelper.data.datastore.MemberPresenceDataStore
import pl.karol202.bphelper.data.entity.MemberPresenceEntity

class InMemoryMemberPresenceDataStore : MemberPresenceDataStore
{
	private val state = MutableStateFlow(emptyMap<Int, MemberPresenceEntity>())

	override val membersPresences: Flow<Map<Int, MemberPresenceEntity>> = state

	override suspend fun setMemberPresence(memberId: Int, memberPresence: MemberPresenceEntity)
	{
		state.value += memberId to memberPresence
	}

	override suspend fun removeMemberPresence(memberId: Int)
	{
		state.value -= memberId
	}
}
