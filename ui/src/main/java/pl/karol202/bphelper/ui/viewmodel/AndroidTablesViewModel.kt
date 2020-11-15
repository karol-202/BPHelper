package pl.karol202.bphelper.ui.viewmodel

import pl.karol202.bphelper.presentation.viewmodel.TablesViewModel

class AndroidTablesViewModel(private val delegate: TablesViewModel) : AndroidViewModelProxy(delegate),
                                                                      TablesViewModel by delegate
