package pl.karol202.bphelper.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.withContext
import pl.karol202.bphelper.data.datastore.MemberDataStore
import pl.karol202.bphelper.data.datastore.MemberPresenceDataStore
import pl.karol202.bphelper.data.entity.MemberEntity
import pl.karol202.bphelper.data.entity.toEntity
import pl.karol202.bphelper.data.entity.toModel
import pl.karol202.bphelper.domain.model.Member
import pl.karol202.bphelper.domain.model.NewMember
import pl.karol202.bphelper.domain.repository.MemberRepository

class MemberRepositoryImpl(private val memberDataStore: MemberDataStore,
                           private val memberPresenceDataStore: MemberPresenceDataStore) : MemberRepository
{
	override val allMembers = memberDataStore.allMembers.zip(memberPresenceDataStore.membersPresences) { members, presences ->
		members.map { member ->
			member.toModel(presences[member.id] ?: MemberEntity.Presence.NONE)
		}
	}

	override suspend fun addMember(member: NewMember) = withContext(Dispatchers.IO) {
		val created = memberDataStore.addMember(member.toEntity())
		memberPresenceDataStore.setMemberPresence(created.id, member.presence.toEntity())
	}

	override suspend fun updateMember(member: Member) = withContext(Dispatchers.IO) {
		memberDataStore.updateMember(member.toEntity())
		memberPresenceDataStore.setMemberPresence(member.id, member.presence.toEntity())
	}

	override suspend fun removeMember(memberId: Long)
	{
		memberDataStore.removeMember(memberId)
		memberPresenceDataStore.removeMemberPresence(memberId)
	}
}
