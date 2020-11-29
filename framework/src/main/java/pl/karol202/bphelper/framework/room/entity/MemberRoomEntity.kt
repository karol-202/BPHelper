package pl.karol202.bphelper.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.bphelper.data.model.MemberModel
import pl.karol202.bphelper.data.model.NewMemberModel

@Entity(tableName = "members")
data class MemberRoomEntity(@PrimaryKey(autoGenerate = true) val id: Long,
                            val name: String)

fun MemberModel.toRoomEntity() = MemberRoomEntity(id, name)
fun NewMemberModel.toRoomEntity() = MemberRoomEntity(0, name)
fun MemberRoomEntity.toEntity() = MemberModel(id, name)
