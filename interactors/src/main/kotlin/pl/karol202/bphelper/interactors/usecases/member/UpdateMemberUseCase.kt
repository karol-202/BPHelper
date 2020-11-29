package pl.karol202.bphelper.interactors.usecases.member

import pl.karol202.bphelper.domain.entity.Member

interface UpdateMemberUseCase
{
	suspend operator fun invoke(member: Member)
}
