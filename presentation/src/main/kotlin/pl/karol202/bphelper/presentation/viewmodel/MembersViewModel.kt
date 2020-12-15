package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.presentation.viewdata.TableConfigurationErrorViewData

interface MembersViewModel : ViewModel
{
	sealed class TableDrawResult
	{
		object GoToTables : TableDrawResult()

		data class Error(val error: TableConfigurationErrorViewData) : TableDrawResult()
	}

	val allMembers: Flow<List<MemberViewData>>
	val tableDrawResult: Flow<TableDrawResult>

	fun addMember(name: String)

	fun updateMember(member: MemberViewData)

	fun removeMember(memberId: Long)

	fun tryToDrawTables()
}
