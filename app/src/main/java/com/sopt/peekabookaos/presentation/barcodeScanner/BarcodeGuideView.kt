package com.sopt.peekabookaos.presentation.barcodeScanner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.sopt.peekabookaos.R

class BarcodeGuideView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val path = Path()
    private val rect = RectF()

    private val stroke = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4f
        color = Color.parseColor("#902b21")
        style = Paint.Style.STROKE
    }

    private val eraser = Paint().apply {
        isAntiAlias = true
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundColor(Color.argb(99, 0, 0, 0))
        drawHole(canvas)
        drawBorder(canvas)
        drawText(canvas)
    }

    /**
     * Text를 그려주는 함수
     */
    private fun drawText(canvas: Canvas) {
        val textView = TextView(context).apply {
            text = context.getText(R.string.barcode_guide)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTextColor(Color.WHITE)
            setTextAppearance(R.style.H2Md)
            gravity = Gravity.CENTER
        }
        textView.measure(0, 0)
        val xPos = (width - textView.measuredWidth) / 2
        val yPos = rect.bottom + textView.measuredHeight
        canvas.translate(xPos.toFloat(), yPos)
        textView.layout(0, 0, textView.measuredWidth, textView.measuredHeight)
        textView.draw(canvas)
    }

    /**
     * Hole의 Border를 그려주는 함수
     */
    private fun drawBorder(canvas: Canvas) {
        path.rewind()
        path.addRect(
            rect.apply {
                updateRect()
            },
            Path.Direction.CW
        )

        canvas.drawPath(path, stroke)
    }

    /**
     * Hole을 그려주는 함수
     */
    private fun drawHole(canvas: Canvas) {
        canvas.drawRect(
            rect.apply {
                updateRect()
            },
            eraser
        )
    }

    /**
     * Hole의 크기를 업데이트 하는 함수
     */
    private fun updateRect(offset: Float = 4f) {
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
