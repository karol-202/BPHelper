package pl.karol202.bphelper.presentation.viewmodel

interface SettingsViewModel : ViewModel
{
	fun getString(key: String): String?

	fun getInt(key: String): Int

	fun getBoolean(key: String): Boolean

	fun putString(key: String, value: String?)

	fun putInt(key: String, value: Int)

	fun putBoolean(key: String, value: Boolean)
}
