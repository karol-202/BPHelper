package pl.karol202.bphelper.ui.viewmodel

import androidx.lifecycle.ViewModel
import java.io.Closeable

abstract class AndroidViewModelProxy(private val closeable: Closeable) : ViewModel()
{
	override fun onCleared() = closeable.close()
}
