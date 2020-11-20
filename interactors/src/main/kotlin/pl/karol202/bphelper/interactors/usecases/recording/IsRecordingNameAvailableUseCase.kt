package pl.karol202.bphelper.interactors.usecases.recording

interface IsRecordingNameAvailableUseCase
{
	operator fun invoke(name: String): Boolean
}
