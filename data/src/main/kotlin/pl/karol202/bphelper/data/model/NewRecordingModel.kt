package pl.karol202.bphelper.data.model

data class NewRecordingModel(val name: String,
                             val extension: String,
                             val inProgress: Boolean)

fun NewRecordingModel.withUri(uri: String) = RecordingModel(uri, name, extension, inProgress)
