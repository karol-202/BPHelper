package pl.karol202.bphelper.ui.settings

import androidx.preference.*

fun PreferenceFragmentCompat.setPreferences(block: PreferenceScreen.() -> Unit)
{
	preferenceScreen = preferenceManager.createPreferenceScreen(preferenceManager.context).apply(block)
}

fun PreferenceGroup.category(block: PreferenceCategory.() -> Unit)
{
	addPreference(PreferenceCategory(preferenceManager.context).apply(block))
}

fun PreferenceGroup.duration(block: DurationPreference.() -> Unit)
{
	addPreference(DurationPreference(preferenceManager.context).apply(block))
}

fun PreferenceGroup.switch(block: SwitchPreference.() -> Unit)
{
	addPreference(SwitchPreference(preferenceManager.context).apply(block))
}

val PreferenceGroup.allPreferencesRecursively: List<Preference>
	get() = (0 until preferenceCount).map { getPreference(it) }.flatMap {
		if(it is PreferenceGroup) it.allPreferencesRecursively
		else listOf(it)
	}

fun TwoStatePreference.setAfterPreferenceChangeListener(listener: (Boolean) -> Unit) =
	setOnPreferenceChangeListener { _, value ->
		listener(value as Boolean)
		true
	}
