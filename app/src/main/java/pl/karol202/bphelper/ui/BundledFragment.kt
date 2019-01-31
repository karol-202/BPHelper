package pl.karol202.bphelper.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import pl.karol202.bphelper.BundleDelegate

abstract class BundledFragment : Fragment()
{
	companion object
	{
		private const val BUNDLE_NAME = "saved_instance_state"
	}

	private val instanceState = Bundle()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		savedInstanceState?.getBundle(BUNDLE_NAME)?.let {
			instanceState.putAll(it)
		}
	}

	override fun onSaveInstanceState(outState: Bundle)
	{
		super.onSaveInstanceState(outState)
		outState.putBundle(BUNDLE_NAME, instanceState)
	}

	protected fun <T : Any> instanceState() =
		BundleDelegate.Nullable<T>(bundleProvider = { instanceState })

	protected fun <T : Any> instanceStateOr(defaultValue: T) =
		BundleDelegate.NotNull(bundleProvider = { instanceState },
		                       defaultValueProvider = { defaultValue })

	protected fun <T : Any> instanceStateOr(defaultValueProvider: () -> T) =
		BundleDelegate.NotNull(bundleProvider = { instanceState },
		                       defaultValueProvider = defaultValueProvider)

	protected fun <T : Any> arguments() =
		BundleDelegate.Nullable<T>(bundleProvider = this::getOrCreateNewArguments)

	protected fun <T : Any> argumentsOr(defaultValue: T) =
		BundleDelegate.NotNull(bundleProvider = this::getOrCreateNewArguments,
		                       defaultValueProvider = { defaultValue })

	protected fun <T : Any> argumentsOr(defaultValueProvider: () -> T) =
		BundleDelegate.NotNull(bundleProvider = this::getOrCreateNewArguments,
		                       defaultValueProvider = defaultValueProvider)

	protected fun <T : Any> argumentsOrThrow() =
		BundleDelegate.NotNull<T>(bundleProvider = this::getOrCreateNewArguments,
		                          defaultValueProvider = { throw IllegalStateException("No argument passed") })

	private fun getOrCreateNewArguments() = arguments ?: Bundle().also { arguments = it }
}