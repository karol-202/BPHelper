package pl.karol202.bphelper.presentation.viewmodel.impl

import pl.karol202.bphelper.domain.model.NewMember
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel

class MembersViewModelImpl(private val memberRepository: MemberRepository) : BaseViewModel(), MembersViewModel
{
	override val allMembers = memberRepository.allMembers

	override fun addMember(name: String) = launch {
		memberRepository.addMember(NewMember(name))
	}
}
