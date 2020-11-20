package pl.karol202.bphelper.framework.controller

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import pl.karol202.bphelper.data.controller.NotificationController
import pl.karol202.bphelper.framework.R
import pl.karol202.bphelper.framework.extensions.doOnApi

class NotificationControllerImpl(private val context: Context,
                                 private val notificationManager: NotificationManagerCompat) : NotificationController
{
	companion object
	{
		const val ID_PREP_TIMER_FINISH = 1
		const val ID_RECORDING = 2
	}

	enum class Channel(val id: String,
	                   @StringRes val nameRes: Int,
	                   @StringRes val descriptionRes: Int,
	                   val importance: Int, //Used only in Android O and higher
	                   val priority: Int) //Used only below Android O
	{
		DEFAULT("default",
		        R.string.notification_channel_prep_time_name,
		        R.string.notification_channel_prep_time_description,
		        android.app.NotificationManager.IMPORTANCE_DEFAULT,
		        NotificationCompat.PRIORITY_DEFAULT)
	}

	init
	{
		registerChannels()
	}

	private fun registerChannels() = Channel.values().forEach { registerChannel(it) }

	private fun registerChannel(channel: Channel) = doOnApi(Build.VERSION_CODES.O) {
		val name = context.getString(channel.nameRes)
		val desc = context.getString(channel.descriptionRes)
		val notificationChannel = NotificationChannel(channel.id, name, channel.importance).apply {
			description = desc
		}
		notificationManager.createNotificationChannel(notificationChannel)
	}

	override fun showPrepTimerFinishNotification() = showNotification(ID_PREP_TIMER_FINISH, Channel.DEFAULT) {
		setContentTitle(context.getString(R.string.notification_prep_time_end))
	}

	fun buildRecordingNotification(intent: PendingIntent) = buildNotification(Channel.DEFAULT) {
		setContentTitle(context.getString(R.string.notification_recorder))
		setContentText(context.getString(R.string.notification_recorder_description))
		setContentIntent(intent)
		setOngoing(true)
	}

	private fun showNotification(id: Int, channel: Channel, block: NotificationCompat.Builder.() -> Unit) =
		notificationManager.notify(id, buildNotification(channel, block))

	private fun buildNotification(channel: Channel, block: NotificationCompat.Builder.() -> Unit): Notification =
		NotificationCompat.Builder(context, channel.id).apply {
			setSmallIcon(R.drawable.ic_notification_24dp)
			priority = channel.priority
		}.apply(block).build()
}