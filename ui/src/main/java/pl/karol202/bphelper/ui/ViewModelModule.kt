package pl.karol202.bphelper.ui

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel
import pl.karol202.bphelper.ui.viewmodel.AndroidMembersViewModel

fun viewModelModule() = module {
	viewModel { AndroidMembersViewModel(get()) }
}
