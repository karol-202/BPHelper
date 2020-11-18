package pl.karol202.bphelper.ui.viewmodel

import pl.karol202.bphelper.presentation.viewmodel.DebateViewModel

class AndroidDebateViewModel(private val delegate: DebateViewModel) : AndroidViewModelProxy(delegate),
                                                                      DebateViewModel by delegate
