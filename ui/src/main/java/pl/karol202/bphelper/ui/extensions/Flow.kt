package pl.karol202.bphelper.ui.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.karol202.bphelper.presentation.util.Event

inline fun <T> Flow<T>.collectIn(scope: CoroutineScope, crossinline action: suspend (value: T) -> Unit)
{
	scope.launch { collect(action) }
}

inline fun <T : Any> Flow<Event<T>>.handleEventsIn(scope: CoroutineScope, crossinline action: (value: T) -> Unit) =
	collectIn(scope) { event -> event.getIfNotConsumed()?.let(action) }
