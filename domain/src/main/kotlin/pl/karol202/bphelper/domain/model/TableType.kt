package pl.karol202.bphelper.domain.model

data class TableType(val size: Int,
                     val role: Role)
{
    enum class Role
    {
        GOV, OPP,
        OG, OO, CG, CO
    }
}
