package com.sopt.peekabookaos.presentation.bookshelf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.R

class BookshelfShelfDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    private var sizeOutSide = 50
    private var sizeInSide = 40

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex
        if (spanIndex != 0) {
            if (spanIndex == 1) {
                // 가운데 아이템
                outRect.left = sizeInSide
                outRect.right = sizeInSide
            } else if (spanIndex == 2) {
                // 오른쪽 아이템
                outRect.left = sizeInSide
                outRect.right = sizeOutSide
            }
        } else {
            // 왼쪽 아이템
            outRect.left = sizeOutSide
            outRect.right = sizeInSide
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.peeka_beige)

        super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom: Float = top + 12f

            c.drawRect(left, top, right, bottom, paint)
        }
    }
}
