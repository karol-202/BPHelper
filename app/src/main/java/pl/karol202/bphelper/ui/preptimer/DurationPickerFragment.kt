package pl.karol202.bphelper.ui.preptimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pl.karol202.bphelper.components.ExtendedDialogFragment
import pl.karol202.bphelper.extensions.ctx
import pl.karol202.bphelper.extensions.setArguments
import pl.karol202.bphelper.extensions.to
import kotlin.time.Duration

class DurationPickerFragment : ExtendedDialogFragment()
{
	interface OnDurationSetListener
	{
		fun onDurationSet(duration: Duration)
	}

	companion object
	{
		private const val TAG = "duration_picker_fragment"

		fun <L> create(initialDuration: Duration, onDurationSetListener: L?)
				where L : Fragment,
				      L : OnDurationSetListener =
			DurationPickerFragment().setArguments(DurationPickerFragment::initialDuration to initialDuration).apply {
				setTargetFragment(onDurationSetListener, -1)
			}
	}

	private val initialDuration by argumentsOr(Duration.ZERO)

	private val onDurationSetListener by lazy { targetFragment as? OnDurationSetListener }

	override fun onCreateDialog(savedInstanceState: Bundle?) =
		DurationPickerDialog(ctx, { onDurationSet(it) }, initialDuration)

	fun show(fragmentManager: FragmentManager) = show(fragmentManager, TAG)

	private fun onDurationSet(duration: Duration)
	{
		onDurationSetListener?.onDurationSet(duration)
	}
}
