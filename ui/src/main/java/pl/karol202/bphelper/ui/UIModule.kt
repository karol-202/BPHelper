package pl.karol202.bphelper.ui

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.karol202.bphelper.ui.viewmodel.AndroidDebateViewModel
import pl.karol202.bphelper.ui.viewmodel.AndroidMembersViewModel
import pl.karol202.bphelper.ui.viewmodel.AndroidPrepTimerViewModel
import pl.karol202.bphelper.ui.viewmodel.AndroidTablesViewModel

fun uiModule() = module {
	viewModel { AndroidMembersViewModel(get()) }
	viewModel { AndroidTablesViewModel(get()) }
	viewModel { AndroidPrepTimerViewModel(get()) }
	viewModel { AndroidDebateViewModel(get()) }
}
