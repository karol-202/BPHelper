package pl.karol202.bphelper.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import pl.karol202.bphelper.R

class Banner : TextView
{
	constructor(context: Context) : this(context, null)

	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.bannerStyle)

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

	fun show(text: CharSequence)
	{
		this.text = text
		visibility = View.VISIBLE
	}

	fun hide()
	{
		visibility = View.GONE
	}
}