package pl.karol202.bphelper.ui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import pl.karol202.bphelper.ComponentWithArguments
import pl.karol202.bphelper.ComponentWithInstanceState
import pl.karol202.bphelper.InstanceState

abstract class ExtendedFragment : Fragment(), ComponentWithInstanceState, ComponentWithArguments
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

abstract class ExtendedDialogFragment : DialogFragment(), ComponentWithInstanceState, ComponentWithArguments
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

abstract class ExtendedAlertDialog : AlertDialog, ComponentWithInstanceState
{
	override val instanceState = InstanceState()

	constructor(context: Context) : super(context)

	constructor(context: Context, themeResId: Int) : super(context, themeResId)

	constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) :
			super(context, cancelable, cancelListener)

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		instanceState.onRestoreInstanceState(savedInstanceState)
	}

	override fun onSaveInstanceState(): Bundle
	{
		val outState = super.onSaveInstanceState()
		instanceState.onSaveInstanceState(outState)
		return outState
	}
}