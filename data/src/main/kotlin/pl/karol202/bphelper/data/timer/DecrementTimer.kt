package pl.karol202.bphelper.data.timer

interface DecrementTimer
{
	interface Factory
	{
		fun create(durationMillis: Long, interval: Long, onTick: (Long) -> Unit, onFinish: () -> Unit): DecrementTimer
	}

	fun start()

	fun stop()
}
