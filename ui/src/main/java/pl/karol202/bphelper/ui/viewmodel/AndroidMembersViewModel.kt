package pl.karol202.bphelper.ui.viewmodel

import pl.karol202.bphelper.presentation.viewmodel.MembersViewModel

class AndroidMembersViewModel(private val delegate: MembersViewModel) : AndroidViewModelProxy(delegate),
                                                                        MembersViewModel by delegate
