package pl.karol202.bphelper.presentation.viewdata

import pl.karol202.bphelper.domain.model.Member

data class MemberViewData(val id: Long,
                          val name: String,
                          val present: Boolean,
                          val ironman: Boolean)

fun Member.toViewData() = MemberViewData(id, name, presence.isPresent, presence.isIronman)
fun MemberViewData.toModel() = Member(id, name, when
{
    present && ironman -> Member.Presence.IRONMAN
    present -> Member.Presence.PRESENT
    else -> Member.Presence.NONE
})
