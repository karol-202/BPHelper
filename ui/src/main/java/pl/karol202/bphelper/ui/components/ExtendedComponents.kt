package pl.karol202.bphelper.ui.components

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class ExtendedFragment : Fragment(), ComponentWithInstanceState, ComponentWithArguments, ComponentWithPermissions
{
	override val ctx get() = requireContext()

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
