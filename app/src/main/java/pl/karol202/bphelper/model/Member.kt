package pl.karol202.bphelper.model

data class Member(val id: Int,
                  val name: String,
                  var present: Boolean = false,
                  var ironman: Boolean = false)
{
	companion object
	{
		const val SEATS_STANDARD = 1
		const val SEATS_IRONMAN = 2
	}

	// TODO Move to business logic
	val occupiedSeats get() = if(ironman) SEATS_IRONMAN else SEATS_STANDARD
}
