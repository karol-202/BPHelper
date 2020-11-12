package pl.karol202.bphelper.framework.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.framework.room.entity.MemberRoomEntity

@Dao
interface MemberDao
{
	@Insert
	fun insert(vararg member: MemberRoomEntity)

	@Update
	fun update(vararg member: MemberRoomEntity)

	@Delete
	fun delete(vararg member: MemberRoomEntity)

	@Query("SELECT * FROM members ORDER BY name ASC")
	fun getAll(): Flow<List<MemberRoomEntity>>
}
