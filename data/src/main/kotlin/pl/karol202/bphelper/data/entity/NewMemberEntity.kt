package pl.karol202.bphelper.data.entity

import pl.karol202.bphelper.domain.model.NewMember

data class NewMemberEntity(val name: String)

fun NewMember.toEntity() = NewMemberEntity(name)
