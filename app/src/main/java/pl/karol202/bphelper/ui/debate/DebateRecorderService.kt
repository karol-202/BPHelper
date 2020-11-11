package pl.karol202.bphelper.ui.debate

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import pl.karol202.bphelper.NotificationPreset
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.doOnApi

class DebateRecorderService : Service()
{
    companion object
    {
	    const val FILENAME_SUFFIX = ".aac"

	    private const val ARG_NOTIFICATION_STOP = "stop"
	    private const val ARG_FILE = "file"
	    private const val ARG_FILENAME = "filename"
	    private const val ARG_CALLBACK = "callback"

    	fun start(context: Context, file: String, filename: String, recordingStopListener: OnRecordingStopListener?) =
		    with(context) {
			    Intent(this, DebateRecorderService::class.java).also { intent ->
				    val recordingCallback = recordingStopListener?.let { RecordingCallback.create(it) }
				    intent.putExtra(ARG_FILENAME, filename)
				    intent.putExtra(ARG_FILE, file)
				    intent.putExtra(ARG_CALLBACK, recordingCallback)
				    doOnApi(Build.VERSION_CODES.O, block = {
					    startForegroundService(intent)
				    }, fallback = {
					    startService(intent)
				    })
			    }
		    }

	    fun stop(context: Context)
	    {
		    val intent = Intent(context, DebateRecorderService::class.java)
			context.stopService(intent)
	    }
    }

	private class RecorderNotificationPreset(context: Context) : NotificationPreset()
	{
		override val title = R.string.notification_recorder
		override val description = R.string.notification_recorder_description
		override val contentIntent = createContentIntent(context)
		override val ongoing = true

		private fun createContentIntent(context: Context): PendingIntent
		{
			val intent = Intent(context, DebateRecorderService::class.java)
			intent.putExtra(ARG_NOTIFICATION_STOP, true)

			return PendingIntent.getService(context, 0, intent, 0)
		}
	}

	private var filename: String? = null
	private var recordingCallback: RecordingCallback? = null

	private val mediaRecorder = MediaRecorder()
	private var started = false
	private var stopped = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
	    if(intent.isNotificationStop()) stop(false)
	    else start(intent)
        return START_NOT_STICKY
    }

	private fun Intent?.isNotificationStop() = this?.getBooleanExtra(ARG_NOTIFICATION_STOP, false) ?: false

	private fun Intent?.getFile() = this?.getStringExtra(ARG_FILE) ?: throw IllegalArgumentException("No file")

	private fun Intent?.getFilename() = this?.getStringExtra(ARG_FILENAME) ?: throw IllegalArgumentException("No filename")

	private fun Intent?.getRecordingCallback() = this?.getParcelableExtra<RecordingCallback>(ARG_CALLBACK)

	private fun start(intent: Intent?)
	{
		filename = intent.getFilename()
		recordingCallback = intent.getRecordingCallback()

		if(started) return

		val notificationPreset = RecorderNotificationPreset(this)
		startForeground(notificationPreset.id, notificationPreset.build(this))
		startRecording(intent.getFile())
		started = true
	}

	private fun stop(error: Boolean)
	{
		if(stopped) return
		stopRecording()
		recordingCallback?.onRecordingStop(error, filename)
		stopSelf()
		stopped = true
	}

	override fun onDestroy()
	{
		super.onDestroy()
		stop(false)
	}

    override fun onBind(intent: Intent?) = null

	private fun startRecording(file: String) = try
	{
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
		mediaRecorder.setOutputFile(file)
		mediaRecorder.prepare()
		mediaRecorder.start()
	}
	catch(e: Exception) { stop(true) }

	private fun stopRecording() = try
	{
		mediaRecorder.stop()
		mediaRecorder.release()
	}
	catch(e: Exception) { e.printStackTrace() } //Probably exception related to stopping without starting in case of exception in startRecording()
}
