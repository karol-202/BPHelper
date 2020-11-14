package pl.karol202.bphelper.framework.datastore

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.serialization.json.Json
import pl.karol202.bphelper.data.datastore.SettingsDataStore
import pl.karol202.bphelper.data.entity.SettingsEntity

private const val SETTINGS_KEY = "settings"

class SharedPrefsSettingsDataStore(private val sharedPrefs: SharedPreferences) : SettingsDataStore
{
	override var settings: SettingsEntity
		get() =
			sharedPrefs.getString(SETTINGS_KEY, null)?.let { json ->
				Json.decodeFromString(SettingsEntity.serializer(), json)
			} ?: SettingsEntity.DEFAULT
		set(value)
		{
			sharedPrefs.edit { putString(SETTINGS_KEY, Json.encodeToString(SettingsEntity.serializer(), value)) }
		}
}
