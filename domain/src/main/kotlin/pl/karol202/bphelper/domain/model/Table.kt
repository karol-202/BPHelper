package pl.karol202.bphelper.domain.model

data class Table(val role: TableType.Role,
                 val members: List<Member>)
