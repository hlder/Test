package com.hld.myapplication2.particles

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

import kotlin.math.abs

class Particle{
    // 粒子池队列
    var next: Particle? = null

    private var x = 0f

    private var y = 0f

    private var stepX = 0f

    private var stepY = 0f

    private var maxRadius = 50f

    private var scale = 0f

    private var stepScale = Math.random().toFloat() * 0.05f + 0.01f

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    fun draw(canvas: Canvas?){
        val radius = maxRadius/  2 * scale
        canvas?.drawCircle(x, y, radius, paint)
    }

    fun nextStatus(width:Int, height:Int){
        scale += stepScale
        x += stepX
        y += stepY

        if(scale > 1){
            stepScale = - stepScale
        }else if(scale < 0){
            scale = 0.0f
            stepScale = abs(stepScale)
            x = width * Math.random().toFloat()
            y = height * Math.random().toFloat()
            stepX = initStep()
            stepY = initStep()
        }
    }

    private fun initStep():Float{
        return Math.random().toFloat() * 4f - 2f
    }
}
