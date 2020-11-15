package pl.karol202.bphelper.interactors.usecases.member

import pl.karol202.bphelper.domain.model.Member

interface GetMembersUseCase
{
	suspend operator fun invoke(): List<Member>
}