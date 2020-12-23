package pl.karol202.bphelper.ui.extensions

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Activity.viewBinding(inflate: (LayoutInflater) -> T) = lazy { inflate(layoutInflater) }
