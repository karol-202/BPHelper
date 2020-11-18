package pl.karol202.bphelper.framework.datastore

import com.tfcporciuncula.flow.FlowSharedPreferences
import kotlinx.coroutines.flow.map
import pl.karol202.bphelper.data.datastore.SettingsDataStore
import pl.karol202.bphelper.data.entity.SettingsEntity
import pl.karol202.bphelper.framework.sharedprefs.FlowSharedPreferencesDao

private const val SETTINGS_KEY = "settings"

class SharedPrefsSettingsDataStore(preferences: FlowSharedPreferences) : SettingsDataStore
{
	private val delegate = FlowSharedPreferencesDao(preferences, SettingsEntity.serializer(), SETTINGS_KEY)

	override val settings get() = delegate.get().map { it ?: SettingsEntity.DEFAULT }
}
