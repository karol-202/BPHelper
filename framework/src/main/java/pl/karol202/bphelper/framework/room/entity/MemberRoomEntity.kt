package pl.karol202.bphelper.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.bphelper.data.entity.MemberEntity
import pl.karol202.bphelper.data.entity.NewMemberEntity

@Entity(tableName = MemberRoomEntity.TABLE)
data class MemberRoomEntity(@PrimaryKey(autoGenerate = true) val id: Int,
                            val name: String)
{
	companion object
	{
		const val TABLE = "members"
	}
}

fun MemberEntity.toRoomEntity() = MemberRoomEntity(id, name)
fun NewMemberEntity.toRoomEntity() = MemberRoomEntity(0, name)
fun MemberRoomEntity.toEntity() = MemberEntity(id, name)
