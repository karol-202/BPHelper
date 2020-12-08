package pl.karol202.bphelper.ui.settings

import androidx.annotation.StringRes
import androidx.preference.*

fun PreferenceFragmentCompat.setPreferences(block: PreferenceScreen.() -> Unit) =
	preferenceManager.createPreferenceScreen(context)
		.also { preferenceScreen = it }
		.block()

fun PreferenceGroup.category(key: String? = null, block: PreferenceCategory.() -> Unit) =
	preference(PreferenceCategory(context), key, block)

fun PreferenceGroup.duration(key: String? = null, block: DurationPreference.() -> Unit) =
	preference(DurationPreference(context), key, block)

fun PreferenceGroup.switch(key: String? = null, block: SwitchPreference.() -> Unit) =
	preference(SwitchPreference(context), key, block)

private fun <P : Preference> PreferenceGroup.preference(preference: P, key: String?, block: P.() -> Unit) =
	preference
		.apply { if(key != null) this.key = key }
		.apply(this::addPreference)
		.block()

fun DialogPreference.setTitleAndDialogTitle(@StringRes titleResId: Int)
{
	setTitle(titleResId)
	setDialogTitle(titleResId)
}
