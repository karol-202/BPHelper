package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.domain.model.Member
import pl.karol202.bphelper.interactors.usecases.member.*
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.presentation.viewdata.toModel
import pl.karol202.bphelper.presentation.viewdata.toViewData
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel

class MembersViewModelImpl(getMembersFlowUseCase: GetMembersFlowUseCase,
                           private val addMemberUseCase: AddMemberUseCase,
                           private val updateMemberUseCase: UpdateMemberUseCase,
                           private val removeMemberUseCase: RemoveMemberUseCase) : BaseViewModel(), MembersViewModel
{
	override val allMembers = getMembersFlowUseCase().map { it.map(Member::toViewData) }

	override fun addMember(name: String) = launch { addMemberUseCase(name) }

	override fun updateMember(member: MemberViewData) = launch { updateMemberUseCase(member.toModel()) }

	override fun removeMember(memberId: Long) = launch { removeMemberUseCase(memberId) }
}
