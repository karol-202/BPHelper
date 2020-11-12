package pl.karol202.bphelper.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import pl.karol202.bphelper.data.entity.MemberEntity
import pl.karol202.bphelper.model.Member
import pl.karol202.bphelper.repository.MemberRepository

class MembersViewModel(private val memberRepository: MemberRepository) : ViewModel()
{
	private data class MemberPresence(val present: Boolean = false,
	                                  val ironman: Boolean = false)

	private val membersPresenceState = MutableStateFlow(emptyMap<Int, MemberPresence>().withDefault { MemberPresence() })

	val allMembers = memberRepository.allMembers.zip(membersPresenceState) { members, presences ->
		members.map { entity ->
			val presence = presences.getValue(entity.id)
			Member(entity.id, entity.name, presence.present, presence.ironman)
		}
	}

	fun addMember(name: String) = memberRepository.addMember(MemberEntity())

	fun updateMember(member: Member)

	fun removeMember(member: Member)
}
