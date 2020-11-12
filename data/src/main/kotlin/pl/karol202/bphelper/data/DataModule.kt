package pl.karol202.bphelper.data

import org.koin.dsl.module
import pl.karol202.bphelper.data.datastore.MemberPresenceDataStore
import pl.karol202.bphelper.data.datastore.impl.InMemoryMemberPresenceDataStore
import pl.karol202.bphelper.data.repository.MemberRepositoryImpl
import pl.karol202.bphelper.domain.repository.MemberRepository

fun dataModule() = module {
	single<MemberRepository> { MemberRepositoryImpl(get(), get()) }

	single<MemberPresenceDataStore> { InMemoryMemberPresenceDataStore() }
}
