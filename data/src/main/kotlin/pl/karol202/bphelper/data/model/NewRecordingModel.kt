package pl.karol202.bphelper.data.model

data class NewRecordingModel(val name: String,
                             val extension: String)

fun NewRecordingModel.withUri(uri: String) = RecordingModel(uri, name, extension)
