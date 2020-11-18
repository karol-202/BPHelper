package pl.karol202.bphelper.presentation

import org.koin.dsl.module
import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel
import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel
import pl.karol202.bphelper.presentation.viewmodel.PrepTimerViewModel
import pl.karol202.bphelper.presentation.viewmodel.TablesViewModel
import pl.karol202.bphelper.presentation.viewmodel.impl.DebateViewModelImpl
import pl.karol202.bphelper.presentation.viewmodel.impl.MembersViewModelImpl
import pl.karol202.bphelper.presentation.viewmodel.impl.PrepTimerViewModelImpl
import pl.karol202.bphelper.presentation.viewmodel.impl.TablesViewModelImpl

fun presentationModule() = module {
	single<MembersViewModel> { MembersViewModelImpl(get(), get(), get(), get(), get()) }
	single<TablesViewModel> { TablesViewModelImpl(get()) }
	single<PrepTimerViewModel> { PrepTimerViewModelImpl(get(), get(), get(), get(), get(), get(), get()) }
	single<DebateViewModel> { DebateViewModelImpl(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}
