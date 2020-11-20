package pl.karol202.bphelper.framework.datastore

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import pl.karol202.bphelper.data.datastore.RecordingDataStore
import pl.karol202.bphelper.data.entity.NewRecordingEntity
import pl.karol202.bphelper.data.entity.RecordingEntity
import pl.karol202.bphelper.data.entity.withUri
import pl.karol202.bphelper.framework.extensions.doOnApi

class RecordingDataStoreImpl(private val context: Context) : RecordingDataStore
{
	override fun createRecording(recording: NewRecordingEntity) =
		context.contentResolver.insert(getAudioUri(), recording.toContentValues())?.let { uri ->
			recording.withUri(uri.toString())
		}

	override fun updateRecording(recording: RecordingEntity)
	{
		context.contentResolver.update(Uri.parse(recording.uri), recording.toContentValues(), null, null)
	}

	override fun isNameAvailable(name: String) =
		context.contentResolver.query(getAudioUri(),
		                              arrayOf(MediaStore.Audio.Media._ID),
		                              "${MediaStore.Video.Media.DISPLAY_NAME} = ?",
		                              arrayOf(name),
		                              null)?.use { !it.moveToFirst() } ?: true

	private fun getAudioUri() = doOnApi(Build.VERSION_CODES.Q, block = {
		MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
	}, fallback = {
		MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
	})

	private fun RecordingEntity.toContentValues() = createContentValues(name, extension, inProgress)

	private fun NewRecordingEntity.toContentValues() = createContentValues(name, extension, inProgress)

	private fun createContentValues(name: String, extension: String, pending: Boolean) = ContentValues().apply {
		put(MediaStore.Audio.Media.DISPLAY_NAME, "$name.$extension")

		doOnApi(Build.VERSION_CODES.Q) {
			put(MediaStore.Audio.Media.IS_PENDING, pending)
		}
	}
}
