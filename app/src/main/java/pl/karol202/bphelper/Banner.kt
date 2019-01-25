package pl.karol202.bphelper

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewManager
import android.widget.TextView
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.banner(): Banner = banner { }

inline fun ViewManager.banner(init: Banner.() -> Unit) =
	ankoView({ context -> Banner(context) }, theme = 0) { init() }

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