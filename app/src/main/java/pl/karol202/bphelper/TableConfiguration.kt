package pl.karol202.bphelper

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class TableConfigurationType(val visibleName: String,
                                  val factory: TableConfigurationFactory)
{
	TYPE_4X2("4x2", TableConfiguration4X2),
	TYPE_2X3("2x3", TableConfiguration2X3)
}

typealias TableConfigurationFactory = TableConfiguration.Factory<*>

abstract class TableConfiguration protected constructor() : Parcelable
{
	abstract class Table : Parcelable
	{
		abstract class Builder<T : Table>
		{
			protected val members = mutableListOf<Member>()

			protected val freeSeats: Int
				get() = size - members.size

			abstract val size: Int

			fun add(member: Member)
			{
				if(isApplicableFor(member)) repeat(member.occupiedSeats) { members.add(member) }
			}

			fun isApplicableFor(member: Member) = freeSeats >= member.occupiedSeats

			abstract fun build(): T?
		}
	}

	abstract class Factory<T : Table>
	{
		protected abstract val seats: Int
		protected abstract val tables: Int

		fun createForMembers(members: List<Member>): TableConfiguration?
		{
			if(!isPossibleForMembers(members)) return null
			val tablesBuilders = List(tables) { newBuilder() }
			members.filter { it.present }.sortedBy { it.ironman }.forEach { member ->
				tablesBuilders.filter { it.isApplicableFor(member) }.random().add(member)
			}
			val tables = tablesBuilders.map { it.build() ?: throw Exception("Table building error") }
			return createFromTables(tables)
		}

		private fun isPossibleForMembers(members: List<Member>) = getRemainingSeatsForMembers(members) == 0

		//Returns positive number if there are too few members and negative number if there are too many members
		fun getRemainingSeatsForMembers(members: List<Member>) = seats - getOccupiedSeatsAmount(members)

		private fun getOccupiedSeatsAmount(members: List<Member>) =
				members.filter { it.present }.map { it.occupiedSeats }.sum()

		protected abstract fun newBuilder(): Table.Builder<T>

		protected abstract fun createFromTables(tables: List<T>): TableConfiguration
	}
}

@Parcelize
data class TableConfiguration4X2(val openingGov: Table,
                                 val openingOpp: Table,
                                 val closingGov: Table,
                                 val closingOpp: Table) : TableConfiguration()
{
	@Parcelize
	data class Table(val first: Member,
	                 val second: Member) : TableConfiguration.Table()
	{
		class Builder : TableConfiguration.Table.Builder<Table>()
		{
			override val size: Int
				get() = 2

			override fun build() = if(freeSeats == 0) Table(members[0], members[1]) else null
		}
	}

	companion object : TableConfiguration.Factory<Table>()
	{
		override val seats: Int
			get() = 8

		override val tables: Int
			get() = 4

		override fun newBuilder() = Table.Builder()

		override fun createFromTables(tables: List<Table>) = TableConfiguration4X2(tables[0], tables[1], tables[2], tables[3])
	}
}

@Parcelize
data class TableConfiguration2X3(val gov: Table,
                                 val opp: Table) : TableConfiguration()
{
	@Parcelize
	data class Table(val first: Member,
	                 val second: Member,
	                 val third: Member) : TableConfiguration.Table()
	{
		class Builder : TableConfiguration.Table.Builder<Table>()
		{
			override val size: Int
				get() = 3

			override fun build() = if(freeSeats == 0) Table(members[0], members[1], members[2]) else null
		}
	}

	companion object : Factory<Table>()
	{
		override val seats: Int
			get() = 6

		override val tables: Int
			get() = 2

		override fun newBuilder() = Table.Builder()

		override fun createFromTables(tables: List<Table>) = TableConfiguration2X3(tables[0], tables[1])
	}
}
