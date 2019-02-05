package pl.karol202.bphelper.debate

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.StyleRes
import kotlinx.android.synthetic.main.dialog_recording_filename.*
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.CustomDialogBuilder

fun Context.recordingFilenameDialog(@StyleRes style: Int = 0, init: RecordingFilenameDialogBuilder.() -> Unit) =
		RecordingFilenameDialogBuilder(this, style).apply(init)

class RecordingFilenameDialogBuilder(context: Context, @StyleRes style: Int = 0) :
	CustomDialogBuilder(context, R.layout.dialog_recording_filename, style)
{
	private var filenameValidityChecker: ((String) -> Boolean)? = null
	private var onFilenameSetListener: ((String) -> Unit)? = null

	init
	{
		initEditText()
		initDialog()
	}

	private fun initEditText()
	{
		editRecordingFilename.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

			override fun afterTextChanged(s: Editable)
			{
				val valid = filenameValidityChecker?.invoke(s.toString()) ?: true
				textInputLayoutRecordingFilename.error = if(valid) null else context.getString(R.string.text_file_exists)
			}
		})
	}

	private fun initDialog()
	{
		setTitle(R.string.alert_recording_filename_title)
		setPositiveButton(R.string.action_record) { _, _ ->
			onFilenameSetListener?.invoke(editRecordingFilename.text.toString())
		}
		setNegativeButton(R.string.action_cancel, null)
	}

	//Returns true if filename is valid, false if is invalid (for example, the file exists)
	fun setFilenameValidityChecker(listener: (String) -> Boolean)
	{
		filenameValidityChecker = listener
	}

	fun setOnFilenameSetListener(listener: (String) -> Unit)
	{
		onFilenameSetListener = listener
	}
}