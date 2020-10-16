package pl.karol202.bphelper.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import pl.karol202.bphelper.R

class SettingsFragment : PreferenceFragmentCompat()
{
	//SharedPreferences does not store a strong reference to listener thus the reference to it must be kept here.
	private val onPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ -> updateAllPreferences() }

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?)
	{
		setPreferencesFromResource(R.xml.preferences, rootKey)
		updateAllPreferences()
		preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(onPreferenceChangeListener)
	}

	private fun updateAllPreferences() =
		preferenceScreen.sharedPreferences.all.keys.mapNotNull { findPreference(it) }.forEach { updatePreference(it) }

	private fun updatePreference(preference: Preference)
	{
		if(preference is DurationPreference)
			preference.summary = preference.duration.format(requireContext())

		if(preference.key == "poiStart")
			preference.isEnabled = (findPreference("poiStartEnabled") as? SwitchPreferenceCompat)?.isChecked ?: true
		else if(preference.key == "poiEnd")
			preference.isEnabled = (findPreference("poiEndEnabled") as? SwitchPreferenceCompat)?.isChecked ?: true
	}

	override fun onDestroy()
	{
		super.onDestroy()
		preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangeListener)
	}

	override fun onDisplayPreferenceDialog(preference: Preference?)
	{
		if(preference is DurationPreference)
		{
			val dialogFragment = DurationPreferenceDialogFragment.create(preference.key)
			dialogFragment.setTargetFragment(this, 0)
			dialogFragment.show(parentFragmentManager, "android.support.v7.preference.PreferenceFragment.DIALOG")
		}
		else super.onDisplayPreferenceDialog(preference)
	}
}
