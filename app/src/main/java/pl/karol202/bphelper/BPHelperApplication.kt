package pl.karol202.bphelper

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.karol202.bphelper.data.dataModule
import pl.karol202.bphelper.repository.repositoryModule
import pl.karol202.bphelper.viewmodel.viewModelModule

class BPHelperApplication : Application()
{
	override fun onCreate()
	{
		super.onCreate()
		startKoin {
			androidContext(this@BPHelperApplication)
			modules(dataModule(), repositoryModule(), viewModelModule())
		}
	}
}
