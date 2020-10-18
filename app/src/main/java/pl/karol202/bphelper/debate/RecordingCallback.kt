package pl.karol202.bphelper.debate

import android.os.*
import kotlinx.android.parcel.Parcelize
import pl.karol202.bphelper.extensions.set
import java.lang.ref.WeakReference

interface OnRecordingStopListener
{
	fun onRecordingStop(error: Boolean, filename: String?)
}

@Parcelize
class RecordingCallback(private val messenger: Messenger) : Parcelable, OnRecordingStopListener
{
	companion object
	{
		private const val MESSAGE_STOP = 1
		private const val ARG_STOP_ERROR = "error"
		private const val ARG_STOP_FILENAME = "filename"

		fun create(onRecordingStopListener: OnRecordingStopListener) =
			RecordingCallback(CallbackHandler(onRecordingStopListener).toMessenger())
	}

	//onRecordingStopListener argument must be a fragment in order to wrapping it with WeakReference made sense
	private class CallbackHandler(onRecordingStopListener: OnRecordingStopListener) : Handler(Looper.getMainLooper())
	{
		private val listenerReference = WeakReference(onRecordingStopListener)

		fun toMessenger() = Messenger(this)

		override fun handleMessage(message: Message) = when(message.what)
		{
			MESSAGE_STOP -> handleStopMessage(message)
			else -> Unit
		}

		private fun handleStopMessage(message: Message)
		{
			val error = message.data.getBoolean(ARG_STOP_ERROR)
			val filename = message.data.getString(ARG_STOP_FILENAME)
			listenerReference.get()?.onRecordingStop(error, filename)
		}
	}

	override fun onRecordingStop(error: Boolean, filename: String?)
	{
		val message = Message.obtain()
		message.what = MESSAGE_STOP
		message.data = prepareDataBundle(error, filename)
		messenger.send(message)
	}

	private fun prepareDataBundle(error: Boolean, filename: String?) = Bundle().apply {
		this[ARG_STOP_ERROR] = error
		this[ARG_STOP_FILENAME] = filename
	}
}
