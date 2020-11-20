package pl.karol202.bphelper.data.controller

interface RecordingController
{
	val recordingExtension: String

	fun start(recordingUri: String, onStop: (error: Boolean) -> Unit)

	fun stop()
}
