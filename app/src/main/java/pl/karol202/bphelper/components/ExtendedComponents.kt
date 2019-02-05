package pl.karol202.bphelper.components

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceDialogFragmentCompat

abstract class ExtendedFragment : Fragment(), ComponentWithInstanceState,
	ComponentWithArguments,
	ComponentWithPermissions
{
	override val ctx: Context
		get() = requireContext()

	override var componentArguments: Bundle?
		get() = arguments
		set(value) { arguments = value }

	override val instanceState = InstanceState()

	private var requestCounter = 0
		get() = field++
	private val requestListeners = mutableMapOf<Int, (Boolean) -> Unit>()

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

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
	{
		if(permissions.size != 1 || grantResults.size != 1) return
		val result = grantResults[0] == PackageManager.PERMISSION_GRANTED
		requestListeners[requestCode]?.invoke(result)
	}

	override fun requestPermission(permission: String, listener: (Boolean) -> Unit)
	{
		val requestCode = requestCounter
		requestListeners[requestCode] = listener
		requestPermissions(arrayOf(permission), requestCode)
	}
}

abstract class ExtendedDialogFragment : DialogFragment(), ComponentWithInstanceState,
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

abstract class ExtendedPreferenceDialogFragmentCompat : PreferenceDialogFragmentCompat(), ComponentWithInstanceState,
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