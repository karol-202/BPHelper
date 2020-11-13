package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.model.Member
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import java.io.Closeable

interface MembersViewModel : Closeable
{
	val allMembers: Flow<List<MemberViewData>>

	fun addMember(name: String)

	fun updateMember(member: MemberViewData)

	fun removeMember(memberId: Int)
}
