package pl.karol202.bphelper.interactors.usecases.preptimer

import kotlin.time.Duration

interface SetPrepTimerDurationUseCase
{
	operator fun invoke(duration: Duration)
}
