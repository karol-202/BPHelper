package pl.karol202.bphelper.ui.extensions

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.reflect.KProperty

val Fragment.act: FragmentActivity
	get() = requireActivity()

val Fragment.ctx: Context
	get() = requireContext()


data class FragmentArgument<T>(val property: KProperty<T>,
                               val value: T?)
{
	val key = property.name
}

infix fun <T> KProperty<T>.to(value: T?) = FragmentArgument(this, value)

fun Fragment.setArguments(vararg args: FragmentArgument<*>): Fragment
{
	val bundle = Bundle()
	args.forEach { bundle[it.key] = it.value }
	arguments = bundle
	return this
}
