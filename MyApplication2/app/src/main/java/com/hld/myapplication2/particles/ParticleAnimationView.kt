package com.hld.myapplication2.particles

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class ParticleAnimationView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context):this(context, null)
    constructor(context: Context,attrs: AttributeSet?):this(context, attrs, 0)
    constructor(context: Context,attrs: AttributeSet?,defStyleAttr: Int):this(context, attrs, defStyleAttr,0)

    private var animJob:Job? = null

    private var isAnim = false

    private val maxParticleNum = 50

    private var particlePoolNum = 0

    private var particlePool:Particle? = null

    private val lifecycleScope:CoroutineScope

    init {
        lifecycleScope = (context as LifecycleOwner).lifecycleScope
    }

    fun startAnim(){
        particlePool = null
        particlePoolNum = 0

        animJob?.cancel()
        animJob = lifecycleScope.launch(Dispatchers.IO) {
            while(isActive){
                // 转到主线程添加
                lifecycleScope.launch(Dispatchers.Main) {
                    addOneParticle()
                    addOneParticle()
                    addOneParticle()
                    addOneParticle()
                    addOneParticle()
                }

                // 子线程改变池子所有粒子状态
                particlePool?.let { doChangeParticleStatus(it) }

                delay(16)
            }
        }

        isAnim = true
        invalidate()
    }

    fun stopAnim(){
        isAnim = false
        animJob?.cancel()
        animJob = null
        particlePool = null
        particlePoolNum = 0
    }

    // 添加粒子
    private fun addOneParticle() {
        if(particlePoolNum < maxParticleNum){
            val particle = Particle()
            particle.next = particlePool
            particlePool = particle
            particlePoolNum++
        }
    }

    // 改变粒子池所有的
    private fun doChangeParticleStatus(particle:Particle){
        particle.next?.let { doChangeParticleStatus(it) }
        particle.nextStatus(width,height)
    }

    // 执行绘制所有的粒子
    private fun doDrawParticle(particle:Particle,canvas: Canvas?){
        particle.next?.let { doDrawParticle(it,canvas) }
        particle.draw(canvas)
    }

    // onDraw
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(isAnim){
            // 绘制所有粒子
            particlePool?.let { doDrawParticle(it,canvas) }
            invalidate()
        }
    }
}