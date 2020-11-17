package pl.karol202.bphelper.framework

import androidx.core.app.NotificationManagerCompat
import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.karol202.bphelper.data.datastore.MemberDataStore
import pl.karol202.bphelper.data.datastore.SettingsDataStore
import pl.karol202.bphelper.data.notification.NotificationManager
import pl.karol202.bphelper.data.timer.DecrementTimer
import pl.karol202.bphelper.framework.datastore.RoomMemberDataStore
import pl.karol202.bphelper.framework.datastore.SharedPrefsSettingsDataStore
import pl.karol202.bphelper.framework.notification.NotificationManagerImpl
import pl.karol202.bphelper.framework.provider.DecrementTimerImpl
import pl.karol202.bphelper.framework.room.LocalDatabase

fun frameworkModule() = module {
	single { LocalDatabase.create(androidContext()) }
	single { get<LocalDatabase>().memberDao() }

	single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
	single { NotificationManagerCompat.from(androidContext()) }

	single<MemberDataStore> { RoomMemberDataStore(get()) }
	single<SettingsDataStore> { SharedPrefsSettingsDataStore(get()) }

	single<DecrementTimer.Factory> { DecrementTimerImpl.Factory }

	single<NotificationManager> { NotificationManagerImpl(androidContext(), get()) }
}
