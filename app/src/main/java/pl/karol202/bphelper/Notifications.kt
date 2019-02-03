package pl.karol202.bphelper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import pl.karol202.bphelper.ui.extensions.doOnApi

object Notifications
{
	enum class Channel(val id: String,
	                   @StringRes val nameRes: Int,
	                   @StringRes val descriptionRes: Int,
	                   val importance: Int, //Used only in Android O and higher
	                   val priority: Int) //Used only below Android O
	{
		DEFAULT("default",
		        R.string.notification_channel_default_name,
		        R.string.notification_channel_default_description,
		        NotificationManager.IMPORTANCE_DEFAULT,
		        NotificationCompat.PRIORITY_DEFAULT)
	}

	var nextNotificationId = 0
		get() = field++
		private set

	fun registerChannels(context: Context) = doOnApi(Build.VERSION_CODES.O) {
		Channel.values().forEach { context.registerChannel(it) }
	}

	private fun Context.registerChannel(channel: Channel) = doOnApi(Build.VERSION_CODES.O) {
		val name = getString(channel.nameRes)
		val description = getString(channel.descriptionRes)
		val notificationChannel = NotificationChannel(channel.id, name, channel.importance)
		notificationChannel.description = description

		getNotificationManager().createNotificationChannel(notificationChannel)
	}

	private fun Context.getNotificationManager() =
		getSystemService<NotificationManager>() ?: throw Exception("NotificationManager not found.")
}

open class NotificationPreset
{
	val id = Notifications.nextNotificationId
	open val channel: Notifications.Channel = Notifications.Channel.DEFAULT
	open val smallIcon: Int = R.drawable.ic_notification_24dp
	open val title: Int? = null
	open val description: Int? = null

	open fun build(context: Context) = NotificationCompat.Builder(context, channel.id).apply {
		setSmallIcon(smallIcon)
		title?.let { setContentTitle(context.getString(it)) }
		description?.let { setContentText(context.getString(it)) }
		priority = channel.priority
	}.build()

	open fun show(context: Context)
	{
		NotificationManagerCompat.from(context).notify(id, build(context))
	}
}
