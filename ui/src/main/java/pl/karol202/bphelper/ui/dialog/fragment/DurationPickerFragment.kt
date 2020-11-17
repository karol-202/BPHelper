package pl.karol202.bphelper.ui.dialog.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pl.karol202.bphelper.ui.components.ExtendedDialogFragment
import pl.karol202.bphelper.ui.dialog.DurationPickerDialog
import pl.karol202.bphelper.ui.extensions.ctx
import pl.karol202.bphelper.ui.extensions.map
import pl.karol202.bphelper.ui.extensions.setArguments
import pl.karol202.bphelper.ui.extensions.to
import kotlin.time.Duration
import kotlin.time.milliseconds

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
			DurationPickerFragment().setArguments(DurationPickerFragment::initialDuration to
					                                      initialDuration.toLongMilliseconds()).apply {
				setTargetFragment(onDurationSetListener, -1)
			}
	}

	private val initialDuration by argumentsOr(0) map { it.milliseconds }

	private val onDurationSetListener by lazy { targetFragment as? OnDurationSetListener }

	override fun onCreateDialog(savedInstanceState: Bundle?) =
		DurationPickerDialog(ctx, { onDurationSet(it) }, initialDuration)

	fun show(fragmentManager: FragmentManager) = show(fragmentManager, TAG)

	private fun onDurationSet(duration: Duration)
	{
		onDurationSetListener?.onDurationSet(duration)
	}
}
