package pl.karol202.bphelper.presentation.util

import kotlin.jvm.Synchronized

class Event<T : Any>(private val content: T)
{
	var consumed = false

	@Synchronized
	fun getIfNotConsumed() = if(!consumed) content.also { consumed = true } else null
}
