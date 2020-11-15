package pl.karol202.bphelper.ui.extensions

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlin.reflect.KProperty

val Fragment.ctx: Context get() = requireContext()

fun Fragment.alertDialog(init: AlertDialog.Builder.() -> Unit) = AlertDialog.Builder(ctx).apply(init)

fun Fragment.showSnackbar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT, block: Snackbar.() -> Unit = {}) =
	showSnackbar(getString(message), duration, block)

fun Fragment.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT, block: Snackbar.() -> Unit = {})
{
	Snackbar.make(view ?: return, message, duration).apply(block).show()
}


data class FragmentArgument<T>(val key: String,
                               val value: T?)

infix fun <T> KProperty<T>.to(value: T?) = FragmentArgument(this.name, value)

fun <F : Fragment> F.setArguments(vararg args: FragmentArgument<*>): F
{
	val bundle = Bundle()
	args.forEach { bundle[it.key] = it.value }
	arguments = bundle
	return this
}
