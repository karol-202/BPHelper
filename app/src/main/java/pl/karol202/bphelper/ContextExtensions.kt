package pl.karol202.bphelper

import android.content.Context
import android.view.View
import org.jetbrains.anko.AnkoContext

val View.ctx: Context
	get() = context

fun AnkoContext.Companion.createFromView(view: View) = create(view.context, view)