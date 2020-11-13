package pl.karol202.bphelper.data.entity

import pl.karol202.bphelper.domain.model.Member

data class MemberEntity(val id: Long,
                        val name: String)
{
    enum class Presence
    {
        NONE, PRESENT, IRONMAN
    }
}

fun Member.toEntity() = MemberEntity(id, name)
fun MemberEntity.toModel(presence: MemberEntity.Presence) = Member(id, name, presence.toModel())

fun Member.Presence.toEntity() = when(this)
{
    Member.Presence.NONE -> MemberEntity.Presence.NONE
    Member.Presence.PRESENT -> MemberEntity.Presence.PRESENT
    Member.Presence.IRONMAN -> MemberEntity.Presence.IRONMAN
}

fun MemberEntity.Presence.toModel() = when(this)
{
    MemberEntity.Presence.NONE -> Member.Presence.NONE
    MemberEntity.Presence.PRESENT -> Member.Presence.PRESENT
    MemberEntity.Presence.IRONMAN -> Member.Presence.IRONMAN
}
