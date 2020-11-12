package pl.karol202.bphelper.framework

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.karol202.bphelper.data.datastore.MemberDataStore
import pl.karol202.bphelper.framework.datastore.RoomMemberDataStore
import pl.karol202.bphelper.framework.room.LocalDatabase

fun frameworkModule() = module {
	single { LocalDatabase.create(androidContext()) }

	single { get<LocalDatabase>().memberDao() }

	single<MemberDataStore> { RoomMemberDataStore(get()) }
}
