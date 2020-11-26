package pl.karol202.bphelper.presentation

import org.koin.dsl.module
import pl.karol202.bphelper.presentation.viewmodel.*
import pl.karol202.bphelper.presentation.viewmodel.impl.*

fun presentationModule() = module {
	single<MembersViewModel> { MembersViewModelImpl(get(), get(), get(), get(), get()) }
	single<TablesViewModel> { TablesViewModelImpl(get()) }
	single<PrepTimerViewModel> { PrepTimerViewModelImpl(get(), get(), get(), get(), get(), get(), get()) }
	single<DebateViewModel> { DebateViewModelImpl(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(),
	                                              get(), get(), get(), get()) }
	single<PermissionViewModel> { PermissionViewModelImpl(get(), get()) }
}
