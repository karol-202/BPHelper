package pl.karol202.bphelper.data.datastore.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.karol202.bphelper.data.datastore.MemberPresenceDataStore
import pl.karol202.bphelper.data.model.MemberModel

class InMemoryMemberPresenceDataStore : MemberPresenceDataStore
{
	private val state = MutableStateFlow(emptyMap<Long, MemberModel.Presence>())

	override val membersPresences: Flow<Map<Long, MemberModel.Presence>> = state

	override suspend fun setMemberPresence(memberId: Long, memberPresence: MemberModel.Presence)
	{
		state.value += memberId to memberPresence
	}

	override suspend fun removeMemberPresence(memberId: Long)
	{
		state.value -= memberId
	}
}
