package pl.karol202.bphelper.debate

import android.os.Handler
import android.os.Message
import android.os.Messenger
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.ref.WeakReference

interface OnRecordingStopListener
{
	fun onRecordingStop()
}

@Parcelize
class RecordingCallback(private val messenger: Messenger) : Parcelable, OnRecordingStopListener
{
	companion object
	{
		private const val MESSAGE_STOP = 1

		fun create(onRecordingStopListener: OnRecordingStopListener) =
			RecordingCallback(CallbackHandler(onRecordingStopListener).toMessenger())
	}

	//onRecordingStopListener argument must be a fragment in order to wrapping it with WeakReference made sense
	private class CallbackHandler(onRecordingStopListener: OnRecordingStopListener) : Handler()
	{
		private val listenerReference = WeakReference(onRecordingStopListener)

		override fun handleMessage(message: Message?)
		{
			if(message?.what == MESSAGE_STOP) listenerReference.get()?.onRecordingStop()
		}

		fun toMessenger() = Messenger(this)
	}

	override fun onRecordingStop()
	{
		val message = Message.obtain()
		message.what = MESSAGE_STOP
		messenger.send(message)
	}
}