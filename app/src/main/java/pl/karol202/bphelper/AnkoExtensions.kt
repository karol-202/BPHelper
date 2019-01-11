package pl.karol202.bphelper

import android.view.View
import org.jetbrains.anko.AnkoContext

fun AnkoContext.Companion.createFromView(view: View) = create(view.context, view)