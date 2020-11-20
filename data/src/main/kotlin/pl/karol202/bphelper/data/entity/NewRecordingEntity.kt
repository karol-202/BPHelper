package pl.karol202.bphelper.data.entity

data class NewRecordingEntity(val name: String,
                              val extension: String,
                              val inProgress: Boolean)

fun NewRecordingEntity.withUri(uri: String) = RecordingEntity(uri, name, extension, inProgress)
