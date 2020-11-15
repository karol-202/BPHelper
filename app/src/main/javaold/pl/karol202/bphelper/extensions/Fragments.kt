package pl.karol202.bphelper.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.reflect.KProperty

val Fragment.act: FragmentActivity get() = requireActivity()
