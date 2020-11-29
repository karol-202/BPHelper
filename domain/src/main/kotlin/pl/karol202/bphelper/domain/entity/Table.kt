package pl.karol202.bphelper.domain.entity

data class Table(val role: TableType.Role,
                 val members: List<Member>)
