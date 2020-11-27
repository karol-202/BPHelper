package pl.karol202.bphelper.ui.settings

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.preference.DialogPreference
import pl.karol202.bphelper.ui.R
import pl.karol202.bphelper.ui.extensions.format
import kotlin.time.Duration
import kotlin.time.milliseconds

class DurationPreference(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
	DialogPreference(context, attrs, defStyleAttr, defStyleRes)
{
	var duration: Duration = Duration.ZERO
		private set(value)
		{
			field = value
			persistInt(value.inMilliseconds.toInt())
			summary = value.format(preferenceManager.context)
		}

	constructor(context: Context) : this(context, null)

	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.dialogPreferenceStyle)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, defStyleAttr)

	init
	{
		dialogLayoutResource = R.layout.dialog_duration_picker
	}

	override fun onGetDefaultValue(a: TypedArray, index: Int) = a.getInt(index, 0)

	override fun onSetInitialValue(defaultValue: Any?)
	{
		val defaultValueInt = (defaultValue as? Int) ?: 0
		duration = getPersistedInt(defaultValueInt).milliseconds
	}

	fun setValue(newDuration: Duration)
	{
		if(callChangeListener(newDuration)) duration = newDuration
	}
}
