package pl.karol202.bphelper

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.forEachChild

class ItemDivider(private val drawable: Drawable) : RecyclerView.ItemDecoration()
{
	@Suppress("DEPRECATION")
	override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State)
	{
		val left = parent.paddingLeft
		val right = parent.width - parent.paddingRight
		parent.forEachChild { child ->
			val params = child.layoutParams as RecyclerView.LayoutParams
			val top = child.bottom + params.bottomMargin
			val bottom = top + drawable.intrinsicHeight
			drawable.bounds = Rect(left, top, right, bottom)
			drawable.draw(canvas)
		}
	}
}
