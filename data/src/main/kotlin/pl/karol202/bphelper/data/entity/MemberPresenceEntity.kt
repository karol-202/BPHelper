package pl.karol202.bphelper.data.entity

import pl.karol202.bphelper.domain.model.Member
import pl.karol202.bphelper.domain.model.NewMember

data class MemberPresenceEntity(val present: Boolean,
                                val ironman: Boolean)
{
    companion object
    {
        val DEFAULT = MemberPresenceEntity(present = false, ironman = false)
    }
}

fun Member.toPresenceEntity() = MemberPresenceEntity(present, ironman)
fun NewMember.toPresenceEntity() = MemberPresenceEntity(present, ironman)
