package pl.karol202.bphelper.ui.extensions

import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Dialog.viewBinding(inflate: (LayoutInflater) -> T) = lazy { inflate(layoutInflater) }

fun AlertDialog.setPositiveButton(@StringRes textRes: Int, listener: DialogInterface.OnClickListener?) =
	setButton(BUTTON_POSITIVE, context.getString(textRes), listener)

fun AlertDialog.setNegativeButton(@StringRes textRes: Int, listener: DialogInterface.OnClickListener?) =
	setButton(BUTTON_NEGATIVE, context.getString(textRes), listener)
