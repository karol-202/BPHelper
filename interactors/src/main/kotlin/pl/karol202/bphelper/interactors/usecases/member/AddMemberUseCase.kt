package pl.karol202.bphelper.interactors.usecases.member

interface AddMemberUseCase
{
	suspend operator fun invoke(name: String)
}
