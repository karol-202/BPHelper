package pl.karol202.bphelper.framework.datastore

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import pl.karol202.bphelper.data.datastore.RecordingDataStore
import pl.karol202.bphelper.data.model.NewRecordingModel
import pl.karol202.bphelper.data.model.RecordingModel
import pl.karol202.bphelper.data.model.withUri
import pl.karol202.bphelper.framework.extensions.doOnApi

private const val RECORDINGS_DIR = "DebateHelper"

class MediaStoreRecordingDataStore(private val context: Context) : RecordingDataStore
{
	override fun createRecording(recording: NewRecordingModel) =
		context.contentResolver.insert(getAudioUri(), recording.toContentValues())?.let { uri ->
			recording.withUri(uri.toString())
		}

	override fun isNameAvailable(name: String, extension: String) =
		context.contentResolver.query(getAudioUri(),
		                              arrayOf(MediaStore.Audio.Media._ID),
		                              "${MediaStore.Audio.Media.DISPLAY_NAME} = ?",
		                              arrayOf(getFileName(name, extension)),
		                              null)?.use { !it.moveToFirst() } ?: true

	private fun getAudioUri() = doOnApi(Build.VERSION_CODES.Q, block = {
		MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
	}, fallback = {
		MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
	})

	private fun NewRecordingModel.toContentValues() = createContentValues(name, extension)

	@Suppress("deprecation")
	private fun createContentValues(name: String, extension: String) = ContentValues().apply {
		put(MediaStore.Audio.Media.DISPLAY_NAME, getFileName(name, extension))
		put(MediaStore.Audio.Media.TITLE, name)
		put(MediaStore.Audio.Media.MIME_TYPE, "audio/aac")

		doOnApi(Build.VERSION_CODES.Q, block = {
			put(MediaStore.Audio.Media.RELATIVE_PATH, "Music/$RECORDINGS_DIR/")
		}, fallback = {
			put(MediaStore.Audio.Media.DATA,
			    "${Environment.getExternalStorageDirectory()}/$RECORDINGS_DIR/${getFileName(name, extension)}")
		})
	}

	private fun getFileName(name: String, extension: String) = "$name.$extension"
}
