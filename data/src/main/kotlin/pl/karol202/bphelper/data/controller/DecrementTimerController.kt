package pl.karol202.bphelper.data.controller

interface DecrementTimerController
{
	interface Factory
	{
		fun create(durationMillis: Long, interval: Long, onTick: (Long) -> Unit, onFinish: () -> Unit):
				DecrementTimerController
	}

	fun start()

	fun stop()
}
