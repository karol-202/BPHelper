package pl.karol202.bphelper.repository.member

import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.dao.MemberDao
import pl.karol202.bphelper.data.entity.MemberEntity
import pl.karol202.bphelper.data.entity.toEntity
import pl.karol202.bphelper.data.entity.toMember
import pl.karol202.bphelper.model.Member

class MemberRepositoryImpl(private val memberDao: MemberDao) : MemberRepository
{
	override val allMembers = memberDao.getAll().map { it.map(MemberEntity::toMember) }

	override fun addMember(member: Member) = memberDao.insert(member.toEntity())

	override fun updateMember(member: Member) = memberDao.update(member.toEntity())

	override fun removeMember(member: Member) = memberDao.delete(member.toEntity())
}
