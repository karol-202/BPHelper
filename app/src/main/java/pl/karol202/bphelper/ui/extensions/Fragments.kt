package pl.karol202.bphelper.ui.extensions

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

val Fragment.act: FragmentActivity
	get() = requireActivity()

val Fragment.ctx: Context
	get() = requireContext()