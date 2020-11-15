package pl.karol202.bphelper.ui.extensions

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

val Fragment.ctx: Context get() = requireContext()

fun Fragment.alertDialog(init: AlertDialog.Builder.() -> Unit) = AlertDialog.Builder(ctx).apply(init)

fun Fragment.showSnackbar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT, block: Snackbar.() -> Unit = {}) =
	showSnackbar(getString(message), duration, block)

fun Fragment.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT, block: Snackbar.() -> Unit = {})
{
	Snackbar.make(view ?: return, message, duration).apply(block).show()
}
