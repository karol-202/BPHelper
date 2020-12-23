package pl.karol202.bphelper.ui.components

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class ExtendedFragment : Fragment, ComponentWithInstanceState, ComponentWithArguments
{
	override var componentArguments: Bundle?
		get() = arguments
		set(value) { arguments = value }

	override val instanceState = InstanceState()

	constructor() : super()

	constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

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
