package pl.karol202.bphelper.data.controller

interface IncrementTimerController
{
	interface Factory
	{
		fun create(initialDurationMillis: Long, interval: Long, onTick: (Long) -> Unit, onFinish: () -> Unit):
				IncrementTimerController
	}

	fun start()

	fun stop()
}
