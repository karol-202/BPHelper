package pl.karol202.bphelper.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import pl.karol202.bphelper.domain.model.Member
import java.io.Closeable

interface MembersViewModel : Closeable
{
	val allMembers: Flow<List<Member>>

	fun addMember(name: String)
}
