package pl.karol202.bphelper.data.controller

interface RecordingController
{
	fun start(filePath: String, onStop: (error: Boolean) -> Unit)

	fun stop()
}
