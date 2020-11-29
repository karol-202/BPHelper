package pl.karol202.bphelper.data.repository

import kotlinx.coroutines.flow.combine
import pl.karol202.bphelper.data.datastore.MemberDataStore
import pl.karol202.bphelper.data.datastore.MemberPresenceDataStore
import pl.karol202.bphelper.data.model.MemberModel
import pl.karol202.bphelper.data.model.toModel
import pl.karol202.bphelper.data.model.toEntity
import pl.karol202.bphelper.domain.entity.Member
import pl.karol202.bphelper.domain.entity.NewMember
import pl.karol202.bphelper.domain.repository.MemberRepository

class MemberRepositoryImpl(private val memberDataStore: MemberDataStore,
                           private val memberPresenceDataStore: MemberPresenceDataStore) : MemberRepository
{
	override val allMembers = memberDataStore.allMembers.combine(memberPresenceDataStore.membersPresences) { members, presences ->
		members.map { member ->
			member.toEntity(presences[member.id] ?: MemberModel.Presence.NONE)
		}
	}

	override suspend fun addMember(member: NewMember)
	{
		val created = memberDataStore.addMember(member.toModel())
		memberPresenceDataStore.setMemberPresence(created.id, member.presence.toModel())
	}

	override suspend fun updateMember(member: Member)
	{
		memberDataStore.updateMember(member.toModel())
		memberPresenceDataStore.setMemberPresence(member.id, member.presence.toModel())
	}

	override suspend fun removeMember(memberId: Long)
	{
		memberDataStore.removeMember(memberId)
		memberPresenceDataStore.removeMemberPresence(memberId)
	}
}
