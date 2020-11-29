package pl.karol202.bphelper.data.model

import pl.karol202.bphelper.domain.entity.NewMember

data class NewMemberModel(val name: String)

fun NewMember.toModel() = NewMemberModel(name)
