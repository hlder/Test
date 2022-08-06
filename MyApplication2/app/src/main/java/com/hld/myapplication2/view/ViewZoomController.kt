package com.hld.myapplication2.view

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlin.math.abs
import kotlin.math.max

class ViewZoomController(private val view: ViewGroup) {
    private var status = STATUS_INIT

    private val downPoint1 = PointF()
    private val downPoint2 = PointF()

    // 缩放量
    private var downScaleX = 1f
    private var downScaleY = 1f

    // 便宜量
    private var downTranslationX = 0f
    private var downTranslationY = 0f

    fun touchDown(x: Float, y: Float) {
        status = STATUS_MOVE
        downTranslationX = getZoomView()?.translationX ?: 0f
        downTranslationY = getZoomView()?.translationY ?: 0f

        downPoint1.x = x
        downPoint1.y = y
    }

    fun touchDown2(x1: Float, y1: Float, x2: Float, y2: Float) {
        status = STATUS_SCALE

        downTranslationX = getZoomView()?.translationX ?: 0f
        downTranslationY = getZoomView()?.translationY ?: 0f

        downScaleX = getZoomView()?.scaleX ?: 1f
        downScaleY = getZoomView()?.scaleY ?: 1f

        downPoint1.x = x1
        downPoint1.y = y1
        downPoint2.x = x2
        downPoint2.y = y2
    }

    fun touchMove(event: MotionEvent) {
        if (event.pointerCount == 2 && status == STATUS_SCALE) { // 双指缩放
            val scaleX = abs(event.getRawX(0) - event.getRawX(1)) / abs(downPoint1.x - downPoint2.x)
            val scaleY = abs(event.getRawY(0) - event.getRawY(1)) / abs(downPoint1.y - downPoint2.y)

            val scale = max(scaleX * downScaleX, scaleY * downScaleY)
            scale(scale)



            translation(Float.MAX_VALUE, Float.MAX_VALUE)

            val downCenterX = abs(downPoint1.x + downPoint2.x) / 2f
            val downCenterY = abs(downPoint1.y + downPoint2.y) / 2f

            val xBl = downCenterX / view.width
            val yBl = downCenterY / view.height

            val zoomView = getZoomView() ?: return
            val realWidth = zoomView.width * zoomView.scaleX
            val realHeight = zoomView.height * zoomView.scaleY

            val zoomTX = -realWidth * xBl
            val zoomTY = -realHeight * yBl

            println("========xBl:$xBl yBl:$yBl zoomTx:$zoomTX zoomTy:$zoomTY realWidth:$realWidth realHeight:$realHeight")
            translation(zoomTX, zoomTY)


//            val zoomView = getZoomView() ?: return
//            val realWidth = zoomView.width * zoomView.scaleX
//            val realHeight = zoomView.height * zoomView.scaleY
//
//            val downCenterX = abs(downPoint1.x + downPoint2.x) / 2f
//            val downCenterY = abs(downPoint1.y + downPoint2.y) / 2f
//
//            val nowCenterX = abs(event.getRawX(0) + event.getRawX(1)) / 2f
//            val nowCenterY = abs(event.getRawY(0) + event.getRawY(1)) / 2f
//
//            var tx = realWidth / 2f - nowCenterX
//            tx = tx * scale - tx
//
//            var ty = realHeight / 2f - nowCenterY
//            ty = ty * scale - ty
//
//            translation(tx, ty)
//            val maxX = realWidth / 2f - zoomView.width / 2f


        } else if (status == STATUS_MOVE) { // 单指拖动
            translation(event.rawX - downPoint1.x, event.rawY - downPoint1.y)
        }

        limit()
    }

    fun touchUp(x: Float, y: Float) {
        status = STATUS_INIT
    }

    fun touchCancel(x: Float, y: Float) {
        status = STATUS_INIT
    }

    /**
     * 执行缩放
     */
    private fun scale(scale: Float) {
        getZoomView()?.scaleX = scale
        getZoomView()?.scaleY = scale
    }

    /**
     * 执行移动
     */
    private fun translation(tx: Float, ty: Float) {
        getZoomView()?.translationX = downTranslationX + tx
        getZoomView()?.translationY = downTranslationY + ty
    }

    /**
     * 获取需要缩放的view
     */
    private fun getZoomView(): View? {
        return view.getChildAt(0)
    }

    /**
     * 1.限制缩放大小
     * 2.限制上下左右的移动
     */
    private fun limit() {
        limitScale() // 限制缩放

        limitTranslation() // 限制移动偏移量

    }

    private fun limitScale() {
        val zoomView = getZoomView() ?: return
        if (zoomView.scaleX < 1) {
            zoomView.scaleX = 1f
        }
        if (zoomView.scaleY < 1) {
            zoomView.scaleY = 1f
        }
        println("---------------------------------------------------------")
        println("=========x:${zoomView.x} y:${zoomView.y}")
    }

    private fun limitTranslation() {
        val zoomView = getZoomView() ?: return
        val realWidth = zoomView.width * zoomView.scaleX
        val realHeight = zoomView.height * zoomView.scaleY

        val maxX = realWidth / 2f - zoomView.width / 2f
        if (zoomView.translationX > maxX) { // 限制left边界不超出
            zoomView.translationX = maxX
        }
        if (zoomView.translationX < -maxX) { // 限制right边界不超出
            zoomView.translationX = -maxX
        }

        val maxY = realHeight / 2f - zoomView.height / 2f
        if (zoomView.translationY > maxY) { // 限制top边界不超出
            zoomView.translationY = maxY
        }
        if (zoomView.translationY < -maxY) { // 限制bottom边界不超出
            zoomView.translationY = -maxY
        }
    }

    companion object {
        private const val STATUS_INIT = 0
        private const val STATUS_MOVE = 1
        private const val STATUS_SCALE = 2
    }
}
