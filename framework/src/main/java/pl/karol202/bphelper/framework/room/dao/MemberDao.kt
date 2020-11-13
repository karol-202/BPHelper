package pl.karol202.bphelper.framework.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.framework.room.entity.MemberRoomEntity

@Dao
interface MemberDao
{
	@Insert
	fun insert(member: MemberRoomEntity): Long

	@Update
	fun update(member: MemberRoomEntity)

	@Query("DELETE FROM members WHERE id = :memberId")
	fun delete(memberId: Long)

	@Query("SELECT * FROM members ORDER BY name ASC")
	fun getAll(): Flow<List<MemberRoomEntity>>
}
