package pl.karol202.bphelper.framework.datastore

import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.datastore.MemberDataStore
import pl.karol202.bphelper.data.entity.MemberEntity
import pl.karol202.bphelper.framework.room.dao.MemberDao
import pl.karol202.bphelper.framework.room.entity.MemberRoomEntity
import pl.karol202.bphelper.framework.room.entity.toEntity
import pl.karol202.bphelper.framework.room.entity.toRoomEntity

class RoomMemberDataStore(private val memberDao: MemberDao) : MemberDataStore
{
	override val allMembers = memberDao.getAll().map { it.map(MemberRoomEntity::toEntity) }

	override suspend fun addMember(member: MemberEntity) = memberDao.insert(member.toRoomEntity())

	override suspend fun updateMember(member: MemberEntity) = memberDao.update(member.toRoomEntity())

	override suspend fun removeMember(member: MemberEntity) = memberDao.delete(member.toRoomEntity())
}
