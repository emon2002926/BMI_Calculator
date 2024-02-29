package com.example.bmicalculator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView(context:Context,attrs: AttributeSet):View(context ,attrs) {

    private val circleColor = Color.BLUE
    private val strokeWidth = 20f
    private val paint = Paint()

    init {
        paint.isAntiAlias = true
//        paint.color=circleColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width/2f
        val centerY = height/2f
        val radius = Math.min(centerX,centerY)-strokeWidth/2
        val startAngle = 180f
        val sweepAngle = 180f
        //        canvas.drawCircle(centerX,centerY,radius,paint)


        canvas.drawArc(centerX - radius, centerY - radius, centerX + radius,
            centerY + radius, startAngle, sweepAngle, false, paint)
    }
}