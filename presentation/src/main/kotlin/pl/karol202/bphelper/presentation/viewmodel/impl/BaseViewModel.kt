package pl.karol202.bphelper.presentation.viewmodel.impl

import kotlinx.coroutines.*
import java.io.Closeable

abstract class BaseViewModel : Closeable
{
	private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

	fun launch(block: suspend CoroutineScope.() -> Unit)
	{
		viewModelScope.launch { block() }
	}

	override fun close() = viewModelScope.cancel()
}