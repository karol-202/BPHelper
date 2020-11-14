package pl.karol202.bphelper.interactors.usecases.member

import pl.karol202.bphelper.domain.model.Member

interface UpdateMemberUseCase
{
	suspend operator fun invoke(member: Member)
}
