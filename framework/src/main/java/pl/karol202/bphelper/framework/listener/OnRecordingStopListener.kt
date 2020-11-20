package pl.karol202.bphelper.framework.listener

import android.os.*
import kotlinx.android.parcel.Parcelize
import pl.karol202.bphelper.res.extensions.set
import java.lang.ref.Reference
import java.lang.ref.WeakReference

fun interface OnRecordingStopListener
{
	fun onRecordingStop(error: Boolean)

	// When using this method, strong reference to this object will be held, so be careful not to create memory leaks
	fun toParcelable(): ParcelableOnRecordingStopListener = MessengerOnRecordingStopListener.create(this)
}

interface ParcelableOnRecordingStopListener : OnRecordingStopListener, Parcelable

@Parcelize
private class MessengerOnRecordingStopListener(private val messenger: Messenger) : ParcelableOnRecordingStopListener
{
	companion object
	{
		private const val MESSAGE_ID = 1
		private const val ARG_STOP_ERROR = "error"

		fun create(onRecordingStopListener: OnRecordingStopListener) =
			MessengerOnRecordingStopListener(CallbackHandler(onRecordingStopListener).toMessenger())
	}

	private class CallbackHandler(private val onRecordingStopListener: OnRecordingStopListener) : Handler(Looper.getMainLooper())
	{
		fun toMessenger() = Messenger(this)

		override fun handleMessage(message: Message) =
			if(message.what == MESSAGE_ID) handleStopMessage(message)
			else Unit

		private fun handleStopMessage(message: Message)
		{
			val error = message.data.getBoolean(ARG_STOP_ERROR)
			onRecordingStopListener.onRecordingStop(error)
		}
	}

	override fun onRecordingStop(error: Boolean) = messenger.send(Message.obtain().apply {
		what = MESSAGE_ID
		data = Bundle().set(ARG_STOP_ERROR to error)
	})
}
