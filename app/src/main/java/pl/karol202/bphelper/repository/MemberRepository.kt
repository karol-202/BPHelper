package pl.karol202.bphelper.repository

import pl.karol202.bphelper.data.dao.MemberDao
import pl.karol202.bphelper.data.entity.MemberEntity

class MemberRepository(private val memberDao: MemberDao)
{
	val allMembers = memberDao.getAll()

	fun addMember(member: MemberEntity) = memberDao.insert(member)

	fun updateMember(member: MemberEntity) = memberDao.update(member)

	fun removeMember(member: MemberEntity) = memberDao.delete(member)
}
