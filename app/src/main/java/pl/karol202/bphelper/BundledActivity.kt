package pl.karol202.bphelper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BundledActivity : AppCompatActivity()
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

	protected fun <T : Any> instanceState() = BundleDelegate.Nullable<T>(instanceState)

	protected fun <T : Any> instanceState(defaultValue: T) = BundleDelegate.NotNull(instanceState) { defaultValue }

	protected fun <T : Any> instanceState(defaultValueProvider: () -> T) = BundleDelegate.NotNull(instanceState, defaultValueProvider)

	protected fun <T : Any> intent() = IntentDelegate.Nullable<T>()

	protected fun <T : Any> intent(defaultValue: T) = IntentDelegate.NotNull { defaultValue }

	protected fun <T : Any> intent(defaultValueProvider: () -> T) = IntentDelegate.NotNull(defaultValueProvider)

	protected fun <T : Any> intentOrThrow() = IntentDelegate.NotNull<T> { throw IllegalStateException("No value passed") }
}