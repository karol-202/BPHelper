package pl.karol202.bphelper.ui.viewmodel

import pl.karol202.bphelper.presentation.viewmodel.PermissionViewModel

class AndroidPermissionViewModel(private val delegate: PermissionViewModel) : AndroidViewModelProxy(delegate),
                                                                              PermissionViewModel by delegate
