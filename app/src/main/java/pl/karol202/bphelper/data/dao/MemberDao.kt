package pl.karol202.bphelper.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.data.entity.MemberEntity

@Dao
interface MemberDao
{
	@Insert
	fun insert(vararg member: MemberEntity)

	@Update
	fun update(vararg member: MemberEntity)

	@Delete
	fun delete(vararg member: MemberEntity)

	@Query("SELECT * FROM members ORDER BY name ASC")
	fun getAll(): Flow<List<MemberEntity>>
}
