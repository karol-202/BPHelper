package pl.karol202.bphelper.framework.datastore

import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.datastore.MemberDataStore
import pl.karol202.bphelper.data.entity.MemberEntity
import pl.karol202.bphelper.data.entity.NewMemberEntity
import pl.karol202.bphelper.framework.room.dao.MemberDao
import pl.karol202.bphelper.framework.room.entity.MemberRoomEntity
import pl.karol202.bphelper.framework.room.entity.toEntity
import pl.karol202.bphelper.framework.room.entity.toRoomEntity

class RoomMemberDataStore(private val memberDao: MemberDao) : MemberDataStore
{
	override val allMembers = memberDao.getAll().map { it.map(MemberRoomEntity::toEntity) }

	override suspend fun addMember(member: NewMemberEntity): MemberEntity
	{
		val createdId = memberDao.insert(member.toRoomEntity())
		return MemberEntity(createdId, member.name)
	}

	override suspend fun updateMember(member: MemberEntity) = memberDao.update(member.toRoomEntity())

	override suspend fun removeMember(memberId: Long) = memberDao.delete(memberId)
}
