package pl.karol202.bphelper.ui.settings

import androidx.preference.PreferenceDataStore
import pl.karol202.bphelper.presentation.viewmodel.SettingsViewModel

class ViewModelPreferenceDataStore(private val settingsViewModel: SettingsViewModel) : PreferenceDataStore()
{
	override fun getString(key: String, defValue: String?) = settingsViewModel.getString(key)

	override fun getInt(key: String, defValue: Int) = settingsViewModel.getInt(key)

	override fun getBoolean(key: String, defValue: Boolean) = settingsViewModel.getBoolean(key)

	override fun putString(key: String, value: String?) = settingsViewModel.putString(key, value)

	override fun putInt(key: String, value: Int) = settingsViewModel.putInt(key, value)

	override fun putBoolean(key: String, value: Boolean) = settingsViewModel.putBoolean(key, value)
}
