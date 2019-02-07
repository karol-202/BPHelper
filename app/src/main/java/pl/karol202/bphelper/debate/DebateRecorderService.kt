package pl.karol202.bphelper.debate

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

	    private const val ARG_STOP = "stop"
	    private const val ARG_FILE = "filename"
	    private const val ARG_CALLBACK = "callback"

    	fun start(context: Context, file: String, recordingStopListener: OnRecordingStopListener?) = with(context) {
		    Intent(this, DebateRecorderService::class.java).also { intent ->
			    val recordingCallback = recordingStopListener?.let { RecordingCallback.create(it) }
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

	class RecorderNotificationPreset(context: Context) : NotificationPreset()
	{
		override val title = R.string.notification_recorder
		override val description = R.string.notification_recorder_description
		override val contentIntent = createContentIntent(context)
		override val ongoing = true

		private fun createContentIntent(context: Context): PendingIntent
		{
			val intent = Intent(context, DebateRecorderService::class.java)
			intent.putExtra(ARG_STOP, true)

			return PendingIntent.getService(context, 0, intent, 0)
		}
	}

	private val mediaRecorder = MediaRecorder()
	private var started = false
	private var stopped = false
	private var recordingCallback: RecordingCallback? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
	    if(intent.getStop()) stop()
	    else
	    {
		    start(intent)
		    recordingCallback = intent.getRecordingCallback()
	    }
        return START_NOT_STICKY
    }

	private fun Intent?.getStop() = this?.getBooleanExtra(ARG_STOP, false) ?: false

	private fun Intent?.getFile() = this?.getStringExtra(ARG_FILE) ?: throw IllegalArgumentException("No file.")

	private fun Intent?.getRecordingCallback() = this?.getParcelableExtra<RecordingCallback>(ARG_CALLBACK)

	private fun start(intent: Intent?)
	{
		if(started) return
		val notificationPreset = RecorderNotificationPreset(this)
		startForeground(notificationPreset.id, notificationPreset.build(this))
		startRecording(intent.getFile())
		started = true
	}

	private fun stop()
	{
		if(stopped) return
		stopRecording()
		recordingCallback?.onRecordingStop()
		stopSelf()
		stopped = true
	}

	override fun onDestroy()
	{
		super.onDestroy()
		stop()
	}

    override fun onBind(intent: Intent?) = null

	private fun startRecording(file: String)
	{
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
		mediaRecorder.setOutputFile(file)
		mediaRecorder.prepare()
		mediaRecorder.start()
	}

	private fun stopRecording()
	{
		mediaRecorder.stop()
		mediaRecorder.release()
	}
}