package pl.karol202.bphelper.presentation.viewdata

import pl.karol202.bphelper.domain.service.RecordingService

enum class RecordingEventViewData
{
	FINISH, ERROR
}

fun RecordingService.Event.toViewData() = when(this)
{
	RecordingService.Event.FINISH -> RecordingEventViewData.FINISH
	RecordingService.Event.ERROR -> RecordingEventViewData.ERROR
}
