package pl.karol202.bphelper.ui.viewmodel

import pl.karol202.bphelper.presentation.viewmodel.SettingsViewModel

class AndroidSettingsViewModel(private val delegate: SettingsViewModel) : AndroidViewModelProxy(delegate),
                                                                          SettingsViewModel by delegate
