package pl.karol202.bphelper.ui.viewmodel

import pl.karol202.bphelper.presentation.viewmodel.PrepTimerViewModel

class AndroidPrepTimerViewModel(private val delegate: PrepTimerViewModel) : AndroidViewModelProxy(delegate),
                                                                            PrepTimerViewModel by delegate
