package pl.karol202.bphelper.ui.extensions

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

val Fragment.ctx: Context get() = requireContext()

fun Fragment.alertDialog(init: AlertDialog.Builder.() -> Unit) = AlertDialog.Builder(ctx).apply(init)
