package pl.karol202.bphelper.framework

import androidx.core.app.NotificationManagerCompat
import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.karol202.bphelper.data.datastore.MemberDataStore
import pl.karol202.bphelper.data.datastore.SettingsDataStore
import pl.karol202.bphelper.data.controller.NotificationController
import pl.karol202.bphelper.data.controller.DecrementTimerController
import pl.karol202.bphelper.data.controller.SoundController
import pl.karol202.bphelper.framework.datastore.RoomMemberDataStore
import pl.karol202.bphelper.framework.datastore.SharedPrefsSettingsDataStore
import pl.karol202.bphelper.framework.controller.NotificationControllerImpl
import pl.karol202.bphelper.framework.controller.DecrementTimerControllerImpl
import pl.karol202.bphelper.framework.controller.SoundControllerImpl
import pl.karol202.bphelper.framework.room.LocalDatabase

fun frameworkModule() = module {
	single { LocalDatabase.create(androidContext()) }
	single { get<LocalDatabase>().memberDao() }

	single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
	single { NotificationManagerCompat.from(androidContext()) }

	single<MemberDataStore> { RoomMemberDataStore(get()) }
	single<SettingsDataStore> { SharedPrefsSettingsDataStore(get()) }

	single<DecrementTimerController.Factory> { DecrementTimerControllerImpl.Factory }
	single<NotificationController> { NotificationControllerImpl(androidContext(), get()) }
	single<SoundController> { SoundControllerImpl(androidContext()) }
}
