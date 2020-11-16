package pl.karol202.bphelper.data

import org.koin.dsl.module
import pl.karol202.bphelper.data.datastore.MemberPresenceDataStore
import pl.karol202.bphelper.data.datastore.impl.InMemoryMemberPresenceDataStore
import pl.karol202.bphelper.data.repository.MemberRepositoryImpl
import pl.karol202.bphelper.data.repository.SettingsRepositoryImpl
import pl.karol202.bphelper.data.service.PrepTimerServiceImpl
import pl.karol202.bphelper.data.service.TableConfigurationServiceImpl
import pl.karol202.bphelper.domain.repository.MemberRepository
import pl.karol202.bphelper.domain.repository.SettingsRepository
import pl.karol202.bphelper.domain.service.PrepTimerService
import pl.karol202.bphelper.domain.service.TableConfigurationService

fun dataModule() = module {
	single<MemberRepository> { MemberRepositoryImpl(get(), get()) }
	single<SettingsRepository> { SettingsRepositoryImpl(get()) }

	single<MemberPresenceDataStore> { InMemoryMemberPresenceDataStore() }

	single<TableConfigurationService> { TableConfigurationServiceImpl() }
	single<PrepTimerService> { PrepTimerServiceImpl(get()) }
}
