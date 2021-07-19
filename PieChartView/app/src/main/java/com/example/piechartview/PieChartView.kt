package com.example.piechartview
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import java.lang.Math.PI


class PieChartView : View {

    private var values: List<Int> = emptyList()
    private var colorsArray: IntArray = intArrayOf(Color.RED, Color.GREEN, Color.BLACK, Color.BLUE, Color.DKGRAY, Color.MAGENTA)
    private var paint = Paint()
    private var rectF = RectF(0F, 0F, width.toFloat(), height.toFloat())
    private var rect = Rect()

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    fun sendData(values: List<Int>) {
        this.values = values
        invalidate()
    }

    private fun assignColor(index: Int) : Paint {
        paint.color = colorsArray[index]

        return paint
    }

    private fun value() : ArrayList<Float> {
        val array = ArrayList<Float>()
        for(value in values) {
            val math = value * (360 / totalValue().toFloat())
            array.add(math)
        }
        return array
    }

    private fun totalValue() : Int {
        var total = 0
        for (value in values) {
            total += value
        }
        return total
    }

    private fun justDraw(canvas: Canvas?, rectF: RectF) {
        var fromAngle = 0.0F
        for (n in values.indices) {
            canvas?.drawArc(rectF, fromAngle,  value()[n],  true, assignColor(n))
            fromAngle += value()[n]
        }
    }

    private fun text(canvas: Canvas?, rect: Rect, rectF: RectF) {
        var fromAngle = 0.0F

        for(n in values.indices) {
            val sweepAngle = value()[n]
            val angle = sweepAngle * PI/180 // in radians
            val percent = value()[n]/360 * 100
            val percentStr = "$percent%"

            paint.color = Color.BLACK
            paint.textSize = 40F
            paint.textAlign = Paint.Align.CENTER

            canvas?.save()

            paint.getTextBounds(percentStr, 0, value()[n].toString().length, rect)
            var x = rectF.centerX() + kotlin.math.cos(angle)
            val y = rectF.centerY() + kotlin.math.sin(angle)
            x -= rect.width() / 2
            canvas?.rotate(
                    fromAngle + (sweepAngle / 2), ((x + rect.exactCenterX()).toFloat()),
                    ((y + rect.exactCenterY()).toFloat())
            )
            canvas?.drawText(percentStr, x.toFloat(), y.toFloat(), paint)
            canvas?.restore()

            fromAngle += sweepAngle
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerX = measuredWidth / 2
        val centerY = measuredHeight / 2
        val left = centerX - width / 2
        val top = centerY - height / 2
        val right = centerX + width / 2
        val bottom = centerY + height / 2

        rect.set(left, top, right, bottom)

        justDraw(canvas, rectF)
        text(canvas, rect, rectF)
    }
}


