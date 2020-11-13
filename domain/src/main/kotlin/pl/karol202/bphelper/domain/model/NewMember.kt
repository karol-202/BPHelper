package pl.karol202.bphelper.domain.model

data class NewMember(val name: String,
                     val present: Boolean = false,
                     val ironman: Boolean = false)
