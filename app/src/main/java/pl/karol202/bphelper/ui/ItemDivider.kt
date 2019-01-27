package pl.karol202.bphelper.ui

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView

class ItemDivider(private val drawable: Drawable) : RecyclerView.ItemDecoration()
{
	override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State)
	{
		val left = parent.paddingLeft
		val right = parent.width - parent.paddingRight
		parent.forEachIndexed { i, child ->
			if(i == parent.childCount - 1) return@forEachIndexed
			val params = child.layoutParams as RecyclerView.LayoutParams
			val top = child.bottom + params.bottomMargin
			val bottom = top + drawable.intrinsicHeight
			drawable.bounds = Rect(left, top, right, bottom)
			drawable.draw(canvas)
		}
	}
}