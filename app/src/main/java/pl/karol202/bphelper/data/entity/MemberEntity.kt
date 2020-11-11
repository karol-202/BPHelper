package pl.karol202.bphelper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.bphelper.model.Member

@Entity(tableName = MemberEntity.TABLE)
data class MemberEntity(@PrimaryKey val id: Int,
                        val name: String)
{
	companion object
	{
		const val TABLE = "members"
	}
}

fun Member.toEntity() = MemberEntity(id, name)
fun MemberEntity.toMember() = Member(id, name)
