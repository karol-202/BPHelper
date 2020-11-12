package pl.karol202.bphelper

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import pl.karol202.bphelper.service.DebateRecorderService
import java.io.File

interface RecordingStateContext
{
	val ctx: Context
	val onRecordingStopListener: OnRecordingStopListener?

	fun updateRecordingButton(@StringRes text: Int)
}

interface RecordingState : Parcelable
{
	fun setContext(stateContext: RecordingStateContext)

	fun onEntering()

	fun onExiting()
}

@Parcelize
class RecordingStateEnabled(val filename: String,
                            val file: File): RecordingState
{
	companion object
	{
		fun create(stateContext: RecordingStateContext, filename: String, file: File) =
			RecordingStateEnabled(filename, file).apply { setContext(stateContext) }
	}

	@IgnoredOnParcel
	private lateinit var stateContext: RecordingStateContext

	override fun setContext(stateContext: RecordingStateContext)
	{
		this.stateContext = stateContext
	}

	override fun onEntering()
	{
		DebateRecorderService.start(stateContext.ctx, file.absolutePath, filename, stateContext.onRecordingStopListener)

		stateContext.updateRecordingButton(R.string.button_debate_recording_disable)
	}

	override fun onExiting() { }
}

@Parcelize
class RecordingStateDisabled: RecordingState
{
	companion object
	{
		fun create(stateContext: RecordingStateContext) =
			RecordingStateDisabled().apply { setContext(stateContext) }
	}

	@IgnoredOnParcel
	private lateinit var stateContext: RecordingStateContext

	override fun setContext(stateContext: RecordingStateContext)
	{
		this.stateContext = stateContext
	}

	override fun onEntering()
	{
		DebateRecorderService.stop(stateContext.ctx)

		stateContext.updateRecordingButton(R.string.button_debate_recording_enable)
	}

	override fun onExiting() { }
}
