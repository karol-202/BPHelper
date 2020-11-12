package pl.karol202.bphelper.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.bphelper.data.entity.MemberEntity

@Entity(tableName = MemberRoomEntity.TABLE)
data class MemberRoomEntity(@PrimaryKey val id: Int,
                            val name: String)
{
	companion object
	{
		const val TABLE = "members"
	}
}

fun MemberEntity.toRoomEntity() = MemberRoomEntity(id, name)
fun MemberRoomEntity.toEntity() = MemberEntity(id, name)
