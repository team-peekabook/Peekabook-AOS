package com.sopt.peekabookaos.presentation.barcodeScanner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class VerticalPreview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val path = Path()
    private val rect = RectF()

    private val stroke = Paint().apply {
        isAntiAlias = false
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    private val eraser = Paint().apply {
        isAntiAlias = true
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawHole(requireNotNull(canvas))
        drawBorder(canvas)
    }

    private fun drawBorder(canvas: Canvas) {
        path.rewind()
        path.addRect(
            rect.apply {
                setRect(4f/*border width*/)
            },
            Path.Direction.CW
        )

        canvas.drawPath(path, stroke)
    }

    private fun drawHole(canvas: Canvas) {
        canvas.drawRect(
            rect.apply {
                setRect()
            },
            eraser
        )
    }

    private fun setRect(offset: Float = 4f) {
        val holeWidth = 312 * (width / 360f)
        val holeHeight = 168 * (width / 360f)

        rect.set(
            ((width - holeWidth) / 2) - offset,
            ((height - holeHeight) / 2) - offset,
            ((width - holeWidth) / 2 + holeWidth) + offset,
            ((height - holeHeight) / 2 + holeHeight) + offset
        )
    }
}
