package pl.karol202.bphelper.domain.entity

data class Member(val id: Long,
                  val name: String,
                  val presence: Presence
)
{
    enum class Presence
    {
        NONE, PRESENT, IRONMAN;

        val isPresent get() = this != NONE
        val isIronman get() = this == IRONMAN
    }
}
