package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.domain.model.Member
import pl.karol202.bphelper.domain.model.NewMember
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.presentation.viewdata.MemberViewData
import pl.karol202.bphelper.presentation.viewdata.toModel
import pl.karol202.bphelper.presentation.viewdata.toViewData
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel

class MembersViewModelImpl(private val memberRepository: MemberRepository) : BaseViewModel(), MembersViewModel
{
	override val allMembers = memberRepository.allMembers.map { it.map(Member::toViewData) }

	override fun addMember(name: String) = launch {
		memberRepository.addMember(NewMember(name, Member.Presence.NONE))
	}

	override fun updateMember(member: MemberViewData) = launch {
		memberRepository.updateMember(member.toModel())
	}

	override fun removeMember(memberId: Long) = launch {
		memberRepository.removeMember(memberId)
	}
}
