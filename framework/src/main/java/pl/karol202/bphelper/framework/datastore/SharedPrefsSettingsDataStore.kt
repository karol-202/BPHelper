package pl.karol202.bphelper.framework.datastore

import android.content.SharedPreferences
import pl.karol202.bphelper.data.datastore.SettingsDataStore
import pl.karol202.bphelper.data.entity.SettingsEntity

class SharedPrefsSettingsDataStore(private val sharedPrefs: SharedPreferences) : SettingsDataStore
{
	override var settings: SettingsEntity
		get() = SettingsEntity()
		set(value) { }
}
