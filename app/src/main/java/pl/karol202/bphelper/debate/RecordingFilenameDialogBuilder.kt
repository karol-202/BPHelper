package pl.karol202.bphelper.debate

import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_recording_filename.*
import pl.karol202.bphelper.R
import pl.karol202.bphelper.extensions.CustomDialogBuilder

fun Context.recordingFilenameDialog(@StyleRes style: Int = 0, init: RecordingFilenameDialogBuilder.() -> Unit) =
		RecordingFilenameDialogBuilder(this, style).apply(init)

class RecordingFilenameDialogBuilder(context: Context, @StyleRes style: Int = 0) :
	CustomDialogBuilder(context, R.layout.dialog_recording_filename, style)
{
	enum class Validity
	{
		VALID, EMPTY, BUSY
	}

	private var filenameValidityChecker: ((String) -> Validity)? = null
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

			override fun afterTextChanged(s: Editable) = updateFilenameValidity(s.toString())
		})
	}

	private fun updateFilenameValidity(filename: String)
	{
		textInputLayoutRecordingFilename.error = when(checkFilenameValidity(filename))
		{
			Validity.VALID -> null
			Validity.EMPTY -> context.getString(R.string.text_filename_empty)
			Validity.BUSY -> context.getString(R.string.text_filename_busy)
		}
	}

	private fun checkFilenameValidity(filename: String) = filenameValidityChecker?.invoke(filename) ?: Validity.VALID

	private fun initDialog()
	{
		setTitle(R.string.alert_recording_filename_title)
		setPositiveButton(R.string.action_record, null)
		setNegativeButton(R.string.action_cancel, null)
	}

	override fun show(): AlertDialog
	{
		val dialog = super.show()
		dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { checkAndApply(dialog) }
		return dialog
	}

	private fun checkAndApply(dialog: AlertDialog)
	{
		val filename = editRecordingFilename.text.toString()
		if(checkFilenameValidity(filename) != Validity.VALID) return
		onFilenameSetListener?.invoke(filename)
		dialog.dismiss()
	}

	fun setFilenameValidityChecker(listener: (String) -> Validity)
	{
		filenameValidityChecker = listener
		updateFilenameValidity(editRecordingFilename.text.toString())
	}

	fun setOnFilenameSetListener(listener: (String) -> Unit)
	{
		onFilenameSetListener = listener
	}
}
