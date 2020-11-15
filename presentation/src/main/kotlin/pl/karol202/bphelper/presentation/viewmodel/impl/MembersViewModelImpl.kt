package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.domain.model.Member
import pl.karol202.bphelper.interactors.usecases.member.AddMemberUseCase
import pl.karol202.bphelper.interactors.usecases.member.GetMembersFlowUseCase
import pl.karol202.bphelper.interactors.usecases.member.RemoveMemberUseCase
import pl.karol202.bphelper.interactors.usecases.member.UpdateMemberUseCase
import pl.karol202.bphelper.interactors.usecases.table.CheckIfTableConfigurationPossibleUseCase
import pl.karol202.bphelper.presentation.util.Event
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.presentation.viewdata.toModel
import pl.karol202.bphelper.presentation.viewdata.toViewData
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel.TableDrawResult

class MembersViewModelImpl(getMembersFlowUseCase: GetMembersFlowUseCase,
                           private val addMemberUseCase: AddMemberUseCase,
                           private val updateMemberUseCase: UpdateMemberUseCase,
                           private val removeMemberUseCase: RemoveMemberUseCase,
                           private val checkIfTableConfigurationPossibleUseCase: CheckIfTableConfigurationPossibleUseCase) :
	BaseViewModel(), MembersViewModel
{
	private val _tableDrawResult = MutableStateFlow<Event<TableDrawResult>?>(null)

	override val allMembers = getMembersFlowUseCase().map { it.map(Member::toViewData) }
	override val tableDrawResult = _tableDrawResult.filterNotNull()

	override fun addMember(name: String) = launch { addMemberUseCase(name) }

	override fun updateMember(member: MemberViewData) = launch { updateMemberUseCase(member.toModel()) }

	override fun removeMember(memberId: Long) = launch { removeMemberUseCase(memberId) }

	override fun tryToDrawTables() = launch {
		_tableDrawResult.value =
			checkIfTableConfigurationPossibleUseCase().fold(ifLeft = { TableDrawResult.Error(it.toViewData()) },
			                                                ifRight = { TableDrawResult.GoToTables }).let { Event(it) }
	}
}
