package pl.karol202.bphelper.ui

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.karol202.bphelper.ui.viewmodel.*

fun uiModule() = module {
	viewModel { AndroidMembersViewModel(get()) }
	viewModel { AndroidTablesViewModel(get()) }
	viewModel { AndroidPrepTimerViewModel(get()) }
	viewModel { AndroidDebateViewModel(get()) }
	viewModel { AndroidPermissionViewModel(get()) }
	viewModel { AndroidSettingsViewModel(get()) }
}
