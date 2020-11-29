package pl.karol202.bphelper.data.model

import pl.karol202.bphelper.domain.entity.Member

data class MemberModel(val id: Long,
                       val name: String)
{
    enum class Presence
    {
        NONE, PRESENT, IRONMAN
    }
}

fun Member.toModel() = MemberModel(id, name)
fun MemberModel.toEntity(presence: MemberModel.Presence) = Member(id, name, presence.toEntity())

fun Member.Presence.toModel() = when(this)
{
    Member.Presence.NONE -> MemberModel.Presence.NONE
    Member.Presence.PRESENT -> MemberModel.Presence.PRESENT
    Member.Presence.IRONMAN -> MemberModel.Presence.IRONMAN
}

fun MemberModel.Presence.toEntity() = when(this)
{
    MemberModel.Presence.NONE -> Member.Presence.NONE
    MemberModel.Presence.PRESENT -> Member.Presence.PRESENT
    MemberModel.Presence.IRONMAN -> Member.Presence.IRONMAN
}
