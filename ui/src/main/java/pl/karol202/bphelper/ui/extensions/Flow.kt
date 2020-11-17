package pl.karol202.bphelper.ui.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.karol202.bphelper.presentation.util.Event
import pl.karol202.bphelper.presentation.util.collectIn

inline fun <T : Any> Flow<Event<T>>.handleEventsIn(scope: CoroutineScope, crossinline action: (value: T) -> Unit) =
	collectIn(scope) { event -> event.getIfNotConsumed()?.let(action) }
