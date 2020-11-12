package pl.karol202.bphelper.data.entity

import pl.karol202.bphelper.domain.model.Member

data class MemberEntity(val id: Int,
                        val name: String)

fun Member.toEntity() = MemberEntity(id, name)
fun Member.toPresenceEntity() = MemberPresenceEntity(present, ironman)
fun MemberEntity.toModel(presence: MemberPresenceEntity) = Member(id, name, presence.present, presence.ironman)
