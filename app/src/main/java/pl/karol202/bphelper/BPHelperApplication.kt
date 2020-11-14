package pl.karol202.bphelper

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.karol202.bphelper.data.dataModule
import pl.karol202.bphelper.interactors.interactorsModule
import pl.karol202.bphelper.framework.frameworkModule
import pl.karol202.bphelper.presentation.presentationModule
import pl.karol202.bphelper.ui.uiModule

class BPHelperApplication : Application()
{
	override fun onCreate()
	{
		super.onCreate()
		startKoin {
			androidContext(this@BPHelperApplication)
			modules(dataModule(), frameworkModule(), interactorsModule(), presentationModule(), uiModule())
		}
	}
}
