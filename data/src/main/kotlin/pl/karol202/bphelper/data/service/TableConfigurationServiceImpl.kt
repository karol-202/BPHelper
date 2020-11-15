package pl.karol202.bphelper.data.service

import pl.karol202.bphelper.domain.model.*
import pl.karol202.bphelper.domain.service.TableConfigurationService
import pl.karol202.bphelper.domain.util.Either
import pl.karol202.bphelper.domain.util.left
import pl.karol202.bphelper.domain.util.right

private val Member.occupiedSeats get() = when(presence)
{
	Member.Presence.IRONMAN -> 2
	Member.Presence.PRESENT -> 1
	Member.Presence.NONE -> 0
}

class TableConfigurationServiceImpl : TableConfigurationService
{
	data class State(val tables: List<TableState>)
	{
		data class TableState(val type: TableType,
		                      val members: List<Member> = emptyList())
		{
			private val occupiedSeats get() = members.sumBy { it.occupiedSeats }
			val remainingSeats get() = type.size - occupiedSeats

			fun withMember(member: Member) = copy(members = members + member)
		}

		fun withUpdatedTable(targetIndex: Int, targetTable: TableState) =
			copy(tables = tables.mapIndexed { index, table -> if(index == targetIndex) targetTable else table })
	}

	override fun createConfiguration(tableConfigurationType: TableConfigurationType, members: List<Member>):
			Either<TableConfigurationError, TableConfiguration>
	{
		val remainingSeats = getRemainingSeats(tableConfigurationType, members)
		if(remainingSeats > 0) return TableConfigurationError.TooFewMembers(remainingSeats).left()
		if(remainingSeats < 0) return TableConfigurationError.TooManyMembers(-remainingSeats).left()

		val sortedMembers = members.filter { it.presence.isPresent }.sortedByDescending { it.presence.isIronman }
		val initialState = State(tableConfigurationType.tableTypes.map { State.TableState(it) })
		val finalState = sortedMembers.fold(initialState) { state, member ->
			val (index, targetTable) = state.tables
				.withIndex()
				.filter { (_, table) -> table.remainingSeats >= member.occupiedSeats }
				.randomOrNull()
				?: return TableConfigurationError.ConfigurationImpossible.left()
			state.withUpdatedTable(index, targetTable.withMember(member))
		}
		return TableConfiguration(finalState.tables.map { Table(it.type.role, it.members) }).right()
	}

	override fun isConfigurationPossible(tableConfigurationType: TableConfigurationType, members: List<Member>):
			Either<TableConfigurationError, Unit>
	{
		val remainingSeats = getRemainingSeats(tableConfigurationType, members)
		return when
		{
			remainingSeats > 0 -> Either.Left(TableConfigurationError.TooFewMembers(remainingSeats))
			remainingSeats < 0 -> Either.Left(TableConfigurationError.TooManyMembers(-remainingSeats))
			else -> createConfiguration(tableConfigurationType, members).mapRight { Unit }
		}
	}

	// Returns positive number if there are too few members and negative number if there are too many members
	private fun getRemainingSeats(tableConfigurationType: TableConfigurationType, members: List<Member>) =
		tableConfigurationType.tableTypes.sumBy { it.size } - members.sumBy { it.occupiedSeats }
}
