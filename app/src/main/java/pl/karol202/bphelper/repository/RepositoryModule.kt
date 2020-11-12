package pl.karol202.bphelper.repository

import org.koin.dsl.module

fun repositoryModule() = module {
	single { MemberRepository(get()) }
}
