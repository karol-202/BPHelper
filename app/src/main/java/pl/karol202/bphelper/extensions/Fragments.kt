package pl.karol202.bphelper.extensions

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.reflect.KProperty

val Fragment.act: FragmentActivity
	get() = requireActivity()

val Fragment.ctx: Context
	get() = requireContext()


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
