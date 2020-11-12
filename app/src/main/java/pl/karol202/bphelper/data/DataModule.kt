package pl.karol202.bphelper.data

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun dataModule() = module {
	single { LocalDatabase.create(androidContext()) }

	single { get<LocalDatabase>().memberDao() }
}
