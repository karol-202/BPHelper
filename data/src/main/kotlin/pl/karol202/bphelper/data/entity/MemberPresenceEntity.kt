package pl.karol202.bphelper.data.entity

data class MemberPresenceEntity(val present: Boolean,
                                val ironman: Boolean)
{
    companion object
    {
        val DEFAULT = MemberPresenceEntity(present = false, ironman = false)
    }
}
