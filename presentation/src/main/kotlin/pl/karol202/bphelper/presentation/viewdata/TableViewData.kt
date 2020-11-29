package pl.karol202.bphelper.presentation.viewdata

import pl.karol202.bphelper.domain.entity.Table
import pl.karol202.bphelper.domain.entity.TableType
import java.io.Serializable

data class TableViewData(val role: Role,
                         val members: List<MemberViewData>) : Serializable
{
    enum class Role
    {
        GOV, OPP,
        OG, OO, CG, CO
    }
}

fun Table.toViewData() = TableViewData(role.toViewData(), members.map { it.toViewData() })
fun TableType.Role.toViewData() = when(this)
{
    TableType.Role.GOV -> TableViewData.Role.GOV
    TableType.Role.OPP -> TableViewData.Role.OPP
    TableType.Role.OG -> TableViewData.Role.OG
    TableType.Role.OO -> TableViewData.Role.OO
    TableType.Role.CG -> TableViewData.Role.CG
    TableType.Role.CO -> TableViewData.Role.CO
}
