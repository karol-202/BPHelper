package pl.karol202.bphelper.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import kotlinx.android.extensions.LayoutContainer

fun Context.alertDialog(init: AlertDialog.Builder.() -> Unit) = AlertDialog.Builder(this).apply(init)

abstract class CustomDialogBuilder(context: Context, @LayoutRes layout: Int, @StyleRes style: Int = 0) :
	AlertDialog.Builder(context, style), LayoutContainer
{
	@SuppressLint("InflateParams")
	final override val containerView: View = LayoutInflater.from(context).inflate(layout, null)

	init
	{
		setView(containerView)
	}

	final override fun setView(view: View?): AlertDialog.Builder = super.setView(view)
}