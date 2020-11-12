package pl.karol202.bphelper.viewmodel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
	viewModel { MembersViewModel(get()) }
}
