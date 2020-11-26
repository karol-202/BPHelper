package pl.karol202.bphelper.data

import org.koin.dsl.module
import pl.karol202.bphelper.data.datastore.MemberPresenceDataStore
import pl.karol202.bphelper.data.datastore.impl.InMemoryMemberPresenceDataStore
import pl.karol202.bphelper.data.repository.MemberRepositoryImpl
import pl.karol202.bphelper.data.repository.SettingsRepositoryImpl
import pl.karol202.bphelper.data.service.*
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.domain.service.*

fun dataModule() = module {
	single<MemberRepository> { MemberRepositoryImpl(get(), get()) }
	single<SettingsRepository> { SettingsRepositoryImpl(get()) }

	single<MemberPresenceDataStore> { InMemoryMemberPresenceDataStore() }

	single<TableConfigurationService> { TableConfigurationServiceImpl() }
	single<PrepTimerService> { PrepTimerServiceImpl(get()) }
	single<DebateTimerService> { DebateTimerServiceImpl(get(), get()) }
	single<NotificationService> { NotificationServiceImpl(get()) }
	single<SoundService> { SoundServiceImpl(get()) }
	single<RecordingService> { RecordingServiceImpl(get(), get()) }
	single<PermissionService> { PermissionServiceImpl(get()) }
}
