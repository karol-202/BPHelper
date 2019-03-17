package pl.karol202.bphelper.tables

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize
import pl.karol202.bphelper.R
import pl.karol202.bphelper.members.Member

enum class TableConfigurationType(@StringRes val visibleName: Int,
                                  private val factory: TableConfigurationFactory)
{
	TYPE_4X2(R.string.table_configuration_4x2, TableConfiguration4X2),
	TYPE_2X3(R.string.table_configuration_2x3, TableConfiguration2X3),
	TYPE_2X4(R.string.table_configuration_2x4, TableConfiguration2X4);

	fun createForMembers(members: List<Member>) = factory.createForMembers(members)

	fun isPossibleForMembers(members: List<Member>) = factory.isPossibleForMembers(members)

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
		val name: Int
		val allMembers: List<Member>
	}

	class TableBuilder<T : Table>(private val size: Int,
	                              private val buildFunction: (List<Member>) -> T?)
	{
		private val members = mutableListOf<Member>()
		private val freeSeats get() = size - members.size

		fun add(member: Member)
		{
			if(isApplicableFor(member)) repeat(member.occupiedSeats) { members.add(member) }
		}

		fun isApplicableFor(member: Member) = freeSeats >= member.occupiedSeats

		fun shuffle() = apply { members.shuffle() }

		fun build() = buildFunction(members)
	}

	abstract class Factory<T : Table>
	{
		protected abstract val tableNames: List<Int>
		protected abstract val seatsPerTable: Int

		private val tables get() = tableNames.size
		private val seats get() = tables * seatsPerTable
		private val ironmansPerTable get() = seatsPerTable / Member.SEATS_IRONMAN // Decimal part discarded
		private val maxIronmans get() = ironmansPerTable * tables

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

		fun isPossibleForMembers(members: List<Member>): Boolean
		{
			if(!checkMembersAmount(members)) return false
			return members.filter { it.present && it.ironman }.size <= maxIronmans
		}

		private fun checkMembersAmount(members: List<Member>) = getRemainingSeatsForMembers(members) == 0

		//Returns positive number if there are too few members and negative number if there are too many members
		fun getRemainingSeatsForMembers(members: List<Member>) = seats - getOccupiedSeatsAmount(members)

		private fun getOccupiedSeatsAmount(members: List<Member>) =
			members.filter { it.present }.map { it.occupiedSeats }.sum()

		private fun createBuilders() = tableNames.map { name ->
			TableBuilder(seatsPerTable) { members -> createTable(name, members) }
		}

		protected abstract fun createTable(name: Int, members: List<Member>): T

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
		override val allMembers get() = listOf(first, second)
	}

	companion object : Factory<Table>()
	{
		override val tableNames = listOf(R.string.table_name_og, R.string.table_name_oo, R.string.table_name_cg, R.string.table_name_co)
		override val seatsPerTable = 2

		override fun createTable(name: Int, members: List<Member>) = Table(name, members[0], members[1])

		override fun createFromTables(tables: List<Table>) =
			TableConfiguration4X2(tables[0], tables[1], tables[2], tables[3])
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
		override val allMembers get() = listOf(first, second, third)
	}

	companion object : Factory<Table>()
	{
		override val tableNames = listOf(R.string.table_name_gov, R.string.table_name_opp)
		override val seatsPerTable = 3

		override fun createTable(name: Int, members: List<Member>) = Table(name, members[0], members[1], members[2])

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
		override val allMembers get() = listOf(first, second, third, fourth)
	}

	companion object : Factory<Table>()
	{
		override val tableNames = listOf(R.string.table_name_gov, R.string.table_name_opp)
		override val seatsPerTable = 4

		override fun createTable(name: Int, members: List<Member>) =
			Table(name, members[0], members[1], members[2], members[3])

		override fun createFromTables(tables: List<Table>) = TableConfiguration2X4(tables[0], tables[1])
	}
}
