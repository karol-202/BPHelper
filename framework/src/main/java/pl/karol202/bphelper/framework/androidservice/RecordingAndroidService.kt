package pl.karol202.bphelper.framework.androidservice

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import org.koin.android.ext.android.inject
import pl.karol202.bphelper.framework.controller.NotificationControllerImpl
import pl.karol202.bphelper.framework.extensions.doOnApi
import pl.karol202.bphelper.framework.extensions.set
import pl.karol202.bphelper.framework.listener.OnRecordingStopListener
import pl.karol202.bphelper.framework.listener.ParcelableOnRecordingStopListener

class RecordingAndroidService : Service()
{
    companion object
    {
	    private const val ARG_NOTIFICATION_STOP = "stop"
	    private const val ARG_FILE_PATH = "file_path"
	    private const val ARG_CALLBACK = "callback"

    	fun start(context: Context, filePath: String, recordingStopListener: OnRecordingStopListener)
	    {
		    val intent = Intent(context, RecordingAndroidService::class.java).set(
			    ARG_FILE_PATH to filePath,
			    ARG_CALLBACK to recordingStopListener.toParcelable(),
		    )
		    doOnApi(Build.VERSION_CODES.O, block = {
			    context.startForegroundService(intent)
		    }, fallback = {
			    context.startService(intent)
		    })
	    }

	    fun stop(context: Context)
	    {
		    val intent = Intent(context, RecordingAndroidService::class.java)
			context.stopService(intent)
	    }
    }

	private val notificationController by inject<NotificationControllerImpl>()

	private var recordingListener: OnRecordingStopListener? = null

	private val mediaRecorder = MediaRecorder()
	private var started = false
	private var stopped = false

	override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
	    if(intent != null)
	    {
		    if(intent.isNotificationStop()) stop(error = false)
		    else start(intent.getFilePath(), intent.getRecordingListener())
	    }
        return START_NOT_STICKY
    }

	override fun onDestroy()
	{
		super.onDestroy()
		stop(error = false)
	}

	private fun Intent.isNotificationStop() = this.getBooleanExtra(ARG_NOTIFICATION_STOP, false)
	private fun Intent.getFilePath() = this.getStringExtra(ARG_FILE_PATH) ?: throw IllegalArgumentException("No file")
	private fun Intent.getRecordingListener() = this.getParcelableExtra<ParcelableOnRecordingStopListener>(ARG_CALLBACK)

	private fun start(file: String, recordingListener: OnRecordingStopListener?)
	{
		this.recordingListener = recordingListener
		if(started) return

		startForeground(NotificationControllerImpl.ID_RECORDING, createForegroundNotification())
		startRecording(file)

		started = true
	}

	private fun createForegroundNotification(): Notification
	{
		val intent = Intent(this, RecordingAndroidService::class.java).set(ARG_NOTIFICATION_STOP to true)
		val pendingIntent = PendingIntent.getService(this, 0, intent, 0)
		return notificationController.buildRecordingNotification(pendingIntent)
	}

	private fun stop(error: Boolean)
	{
		if(stopped) return

		recordingListener?.onRecordingStop(error)
		stopRecording()
		stopSelf()

		stopped = true
	}

	private fun startRecording(filePath: String) = try
	{
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
		mediaRecorder.setOutputFile(filePath)
		mediaRecorder.prepare()
		mediaRecorder.start()
	}
	catch(e: Exception) { stop(true) }

	private fun stopRecording() = try
	{
		mediaRecorder.stop()
		mediaRecorder.release()
	}
	catch(e: Exception) { e.printStackTrace() }
	// Probably an exception related to stopping without having started due to exception in startRecording()
	// TODO Do better logging
}
