package pl.karol202.bphelper.domain.model

data class Member(val id: Long,
                  val name: String,
                  val presence: Presence)
{
    enum class Presence
    {
        NONE, PRESENT, IRONMAN
    }
}
