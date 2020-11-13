package pl.karol202.bphelper.presentation

import org.koin.dsl.module
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel
import pl.karol202.bphelper.presentation.viewmodel.impl.MembersViewModelImpl

fun presentationModule() = module {
	single<MembersViewModel> { MembersViewModelImpl(get()) }
}
