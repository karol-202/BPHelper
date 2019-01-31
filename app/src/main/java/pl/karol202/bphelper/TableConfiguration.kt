package pl.karol202.bphelper

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

enum class TableConfigurationType(@StringRes val visibleName: Int,
                                  private val factory: TableConfigurationFactory)
{
	TYPE_4X2(R.string.table_configuration_4x2, TableConfiguration4X2),
	TYPE_2X3(R.string.table_configuration_2x3, TableConfiguration2X3),
	TYPE_2X4(R.string.table_configuration_2x4, TableConfiguration2X4);

	fun createForMembers(members: List<Member>) = factory.createForMembers(members)

	fun getRemainingSeatsForMembers(members: List<Member>) = factory.getRemainingSeatsForMembers(members)

	companion object
	{
		fun findByName(name: String) = values().first { it.name == name }
	}
}

typealias TableConfigurationFactory = TableConfiguration.Factory<*>

sealed class TableConfiguration : Parcelable
{
	interface Table : Parcelable
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

			fun shuffle() = apply { members.shuffle() }

			abstract fun build(): T?
		}

		val name: Int
		val allMembers: List<Member>
	}

	abstract class Factory<T : Table>
	{
		protected abstract val seats: Int

		fun createForMembers(members: List<Member>): TableConfiguration?
		{
			if(!isPossibleForMembers(members)) return null
			val tablesBuilders = createBuilders()
			members.filter { it.present }.sortedByDescending { it.ironman }.forEach { member ->
				tablesBuilders.filter { it.isApplicableFor(member) }.random().add(member)
			}
			val tables = tablesBuilders.map { it.shuffle().build() ?: throw Exception("Table building error") }
			return createFromTables(tables)
		}

		private fun isPossibleForMembers(members: List<Member>) = getRemainingSeatsForMembers(members) == 0

		//Returns positive number if there are too few members and negative number if there are too many members
		fun getRemainingSeatsForMembers(members: List<Member>) = seats - getOccupiedSeatsAmount(members)

		private fun getOccupiedSeatsAmount(members: List<Member>) =
				members.filter { it.present }.map { it.occupiedSeats }.sum()

		protected abstract fun createBuilders(): List<Table.Builder<T>>

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
	data class Table(@StringRes override val name: Int,
	                 val first: Member,
	                 val second: Member) : TableConfiguration.Table
	{
		class Builder(@StringRes val name: Int) : TableConfiguration.Table.Builder<Table>()
		{
			override val size: Int
				get() = 2

			override fun build() = if(freeSeats == 0) Table(name, members[0], members[1]) else null
		}

		override val allMembers: List<Member>
			get() = listOf(first, second)
	}

	companion object : TableConfiguration.Factory<Table>()
	{
		private val tableNames = listOf(R.string.table_name_og,
		                                R.string.table_name_oo,
		                                R.string.table_name_cg,
		                                R.string.table_name_co)

		override val seats: Int
			get() = 8

		override fun createBuilders() = tableNames.map { Table.Builder(it) }

		override fun createFromTables(tables: List<Table>) = TableConfiguration4X2(tables[0], tables[1], tables[2], tables[3])
	}
}

@Parcelize
data class TableConfiguration2X3(val gov: Table,
                                 val opp: Table) : TableConfiguration()
{
	@Parcelize
	data class Table(@StringRes override val name: Int,
	                 val first: Member,
	                 val second: Member,
	                 val third: Member) : TableConfiguration.Table
	{
		class Builder(@StringRes val name: Int) : TableConfiguration.Table.Builder<Table>()
		{
			override val size: Int
				get() = 3

			override fun build() = if(freeSeats == 0) Table(name, members[0], members[1], members[2]) else null
		}

		override val allMembers: List<Member>
			get() = listOf(first, second, third)
	}

	companion object : Factory<Table>()
	{
		private val tableNames = listOf(R.string.table_name_gov,
		                                R.string.table_name_opp)

		override val seats: Int
			get() = 6

		override fun createBuilders() = tableNames.map { Table.Builder(it) }

		override fun createFromTables(tables: List<Table>) = TableConfiguration2X3(tables[0], tables[1])
	}
}

@Parcelize
data class TableConfiguration2X4(val gov: Table,
                                 val opp: Table) : TableConfiguration()
{
	@Parcelize
	data class Table(@StringRes override val name: Int,
	                 val first: Member,
	                 val second: Member,
	                 val third: Member,
	                 val fourth: Member) : TableConfiguration.Table
	{
		class Builder(@StringRes val name: Int) : TableConfiguration.Table.Builder<Table>()
		{
			override val size: Int
				get() = 4

			override fun build() = if(freeSeats == 0) Table(name, members[0], members[1], members[2], members[3]) else null
		}

		override val allMembers: List<Member>
			get() = listOf(first, second, third, fourth)
	}

	companion object : Factory<Table>()
	{
		private val tableNames = listOf(R.string.table_name_gov,
		                                R.string.table_name_opp)

		override val seats: Int
			get() = 8

		override fun createBuilders() = tableNames.map { Table.Builder(it) }

		override fun createFromTables(tables: List<Table>) = TableConfiguration2X4(tables[0], tables[1])
	}
}
