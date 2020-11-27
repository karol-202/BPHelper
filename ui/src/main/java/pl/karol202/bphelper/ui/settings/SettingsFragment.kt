package pl.karol202.bphelper.ui.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.TwoStatePreference
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.karol202.bphelper.presentation.viewdata.SettingsKeys
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.extensions.format
import pl.karol202.bphelper.ui.viewmodel.AndroidSettingsViewModel

class SettingsFragment : PreferenceFragmentCompat()
{
	private val settingsViewModel by viewModel<AndroidSettingsViewModel>()

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?)
	{
		preferenceManager.preferenceDataStore = ViewModelPreferenceDataStore(settingsViewModel)

		setPreferences {
			category {
				setTitle(R.string.preference_speech_category)

				duration {
					key = SettingsKeys.SPEECH_DURATION
					setTitle(R.string.preference_speech_duration)
				}
				duration {
					key = SettingsKeys.SPEECH_DURATION_MAX
					setTitle(R.string.preference_speech_duration_max)
				}
			}

			category {
				setTitle(R.string.preference_poi_start_category)

				switch {
					key = SettingsKeys.POI_START_ENABLED
					setTitle(R.string.preference_poi_start_enabled)
					setAfterPreferenceChangeListener { value ->
						findPreference<Preference>(SettingsKeys.POI_START)?.isEnabled = value
					}
				}
				duration {
					key = SettingsKeys.POI_START
					setTitle(R.string.preference_poi_start)
				}
			}

			category {
				setTitle(R.string.preference_poi_end_category)

				switch {
					key = SettingsKeys.POI_END_ENABLED
					setTitle(R.string.preference_poi_end_enabled)
					setAfterPreferenceChangeListener { value ->
						findPreference<Preference>(SettingsKeys.POI_END)?.isEnabled = value
					}
				}
				duration {
					key = SettingsKeys.POI_END
					setTitle(R.string.preference_poi_end)
				}
			}
		}
	}

	override fun onDisplayPreferenceDialog(preference: Preference?) = when(preference)
	{
		is DurationPreference -> showDurationPreferenceDialog(preference)
		else -> super.onDisplayPreferenceDialog(preference)
	}

	private fun showDurationPreferenceDialog(preference: Preference)
	{
		val dialogFragment = DurationPreferenceDialogFragment.create(preference.key)
		dialogFragment.setTargetFragment(this, 0)
		dialogFragment.show(parentFragmentManager, "android.support.v7.preference.PreferenceFragment.DIALOG")
	}
}
