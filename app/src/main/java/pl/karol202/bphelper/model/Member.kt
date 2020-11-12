package pl.karol202.bphelper.model

data class Member(val id: Int,
                  val name: String,
                  var present: Boolean = false,
                  var ironman: Boolean = false)
