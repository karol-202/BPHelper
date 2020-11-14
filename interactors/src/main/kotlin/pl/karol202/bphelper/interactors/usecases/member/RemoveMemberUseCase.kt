package pl.karol202.bphelper.interactors.usecases.member

interface RemoveMemberUseCase
{
	suspend operator fun invoke(memberId: Long)
}
