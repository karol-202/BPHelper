package pl.karol202.bphelper.ui.components

import android.os.Bundle
import androidx.preference.PreferenceDialogFragmentCompat

abstract class ExtendedPreferenceDialogFragmentCompat : PreferenceDialogFragmentCompat(),
                                                        ComponentWithInstanceState,
                                                        ComponentWithArguments
{
	override var componentArguments: Bundle?
		get() = arguments
		set(value) { arguments = value }

	override val instanceState = InstanceState()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		instanceState.onRestoreInstanceState(savedInstanceState)
	}

	override fun onSaveInstanceState(outState: Bundle)
	{
		super.onSaveInstanceState(outState)
		instanceState.onSaveInstanceState(outState)
	}
}
