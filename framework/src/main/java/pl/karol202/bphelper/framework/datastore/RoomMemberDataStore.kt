package pl.karol202.bphelper.framework.datastore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pl.karol202.bphelper.data.datastore.MemberDataStore
import pl.karol202.bphelper.data.model.MemberModel
import pl.karol202.bphelper.data.model.NewMemberModel
import pl.karol202.bphelper.framework.room.dao.MemberDao
import pl.karol202.bphelper.framework.room.entity.MemberRoomEntity
import pl.karol202.bphelper.framework.room.entity.toEntity
import pl.karol202.bphelper.framework.room.entity.toRoomEntity

class RoomMemberDataStore(private val memberDao: MemberDao) : MemberDataStore
{
	override val allMembers = memberDao.getAll().map { it.map(MemberRoomEntity::toEntity) }

	override suspend fun addMember(member: NewMemberModel) = withContext(Dispatchers.IO) {
		val createdId = memberDao.insert(member.toRoomEntity())
		MemberModel(createdId, member.name)
	}

	override suspend fun updateMember(member: MemberModel) = withContext(Dispatchers.IO) {
		memberDao.update(member.toRoomEntity())
	}

	override suspend fun removeMember(memberId: Long) = withContext(Dispatchers.IO) {
		memberDao.delete(memberId)
	}
}
