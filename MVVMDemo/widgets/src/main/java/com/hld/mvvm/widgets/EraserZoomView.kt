package com.hld.mvvm.widgets

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * 支持缩放，双指滑动的擦除控件
 */
class EraserZoomView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private val eraserView: EraserView = EraserView(context)

    private var animatorSet: AnimatorSet? = null

    private var baseExcursionX = 0f
    private var baseExcursionY = 0f

    // x的偏移量
    private var excursionLeft = 0f

    // y的偏移量
    private var excursionTop = 0f

    private val centerPoint: PointF = PointF()

    private var zoom = INIT_ZOOM
        set(value) {
            field = value
            eraserView.zoom = zoom
        }

    private var eraserViewWidth = 0
    private var eraserViewHeight = 0

    private val downPoint1: PointF = PointF()
    private val downPoint2: PointF = PointF()
    private var downZoom = INIT_ZOOM
    private var downExcursionX = 0f //按下时的x的偏移量
    private var downExcursionY = 0f //按下时的y的偏移量

    private val downCenterPoint: PointF = PointF()

    init {
        eraserView.setBackgroundColor(Color.GREEN)
        addView(eraserView)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        initEraserViewSize()
        resetEraserViewLayout()
    }

    // 刷新擦除控件的布局
    private fun resetEraserViewLayout() {
        val nowWidth = (eraserViewWidth * zoom)
        val nowHeight = (eraserViewHeight * zoom)
        val l = excursionLeft
        val t = excursionTop
        val r = l + nowWidth
        val b = t + nowHeight

        eraserView.layout(l.toInt(), t.toInt(), r.toInt(), b.toInt())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (animatorSet?.isRunning == true) { // 如果正在动画中，则拦截，但不处理
            return true
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            if (event.pointerCount > 1) {
                val nowPoint1 = PointF(event.getX(0), event.getY(0))
                val nowPoint2 = PointF(event.getX(1), event.getY(1))

                // 计算双指按下时的两指间距，计算缩放
                val downGap = distance(downPoint1, downPoint2)
                // 计算当前双指的间距
                val nowGap = distance(nowPoint1, nowPoint2)
                // 计算缩放比例
                if (downGap != 0f) {
                    zoom = downZoom * nowGap / downGap
                    if (zoom < INIT_ZOOM) {
                        zoom = INIT_ZOOM
                    }
                }

                // 按下时两指中心点
                val downCenterX = (downPoint1.x + downPoint2.x) / 2
                val downCenterY = (downPoint1.y + downPoint2.y) / 2

                // 现在的两指的中心点
                val centerX = (nowPoint1.x + nowPoint2.x) / 2
                val centerY = (nowPoint1.y + nowPoint2.y) / 2

                val downWidth = eraserViewWidth * downZoom // 按下时的宽度
                var downWidthBl = (downCenterX - downExcursionX) / downWidth // 按下时所占控件的比例
                if (downWidthBl > 1) {
                    downWidthBl = 1f
                }
                if (downWidthBl < 0) {
                    downWidthBl = 0f
                }
                val nowWidth = eraserViewWidth * zoom // 最新的宽度
                val zoomWidth = nowWidth - eraserViewWidth * downZoom // 缩放的宽度
                excursionLeft = downExcursionX + (centerX - downCenterX) - zoomWidth * downWidthBl

                val downHeight = eraserViewHeight * downZoom // 按下时的宽度
                var downHeightBl = (downCenterY - downExcursionY) / downHeight // 按下时所占控件的比例
                if (downHeightBl > 1) {
                    downHeightBl = 1f
                }
                if (downHeightBl < 0) {
                    downHeightBl = 0f
                }
                val nowHeight = eraserViewHeight * zoom // 最新的高度
                val zoomHeight = nowHeight - eraserViewHeight * downZoom // 缩放的高度
                excursionTop = downExcursionY + (centerY - downCenterY) - zoomHeight * downHeightBl

                // 偏移量需要限制
                limitExcursion()

                // 刷新子控件的大小和位置
                resetEraserViewLayout()

                println("==============zoom:$zoom excursionLeft:$excursionLeft excursionTop:$excursionTop")
            }
        }
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if ((ev.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) { // 多指触控进行拦截
            if (ev.pointerCount > 1) {
                downPoint1.x = ev.getX(0)
                downPoint1.y = ev.getY(0)
                downPoint2.x = ev.getX(1)
                downPoint2.y = ev.getY(1)

                downCenterPoint.x = centerPoint.x
                downCenterPoint.y = centerPoint.y
                downZoom = zoom

                downExcursionX = excursionLeft
                downExcursionY = excursionTop
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (animatorSet?.isRunning == true) { // 如果正在动画中，则拦截，但不处理
            return true
        }
        if ((ev.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) { // 多指触控进行拦截
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    /**
     * 计算两个point的间距
     */
    private fun distance(p1: PointF, p2: PointF): Float {
        val distance =
            sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y).toDouble())
        return abs(distance).toFloat()
    }

    /**
     * 偏移量限制
     */
    private fun limitExcursion() {
        val nowWidth = eraserViewWidth * zoom // 最新的宽度
        val nowHeight = eraserViewHeight * zoom // 最新的高度

        if (excursionLeft > baseExcursionX) {
            excursionLeft = baseExcursionX
        }
        if ((excursionLeft + nowWidth) < (width - baseExcursionX)) {
            excursionLeft = width - nowWidth - baseExcursionX
        }

        if (excursionTop > baseExcursionY) {
            excursionTop = baseExcursionY
        }
        if ((excursionTop + nowHeight) < (height - baseExcursionY)) {
            excursionTop = height - nowHeight - baseExcursionY
        }
    }

    /**
     * 初始化擦除控件的size
     */
    private fun initEraserViewSize() {
        if (width > 0 && height > 0) {
            val bitmap = eraserView.bitmap
            bitmap?.let {
                val bitmapBl = bitmap.width.toFloat() / bitmap.height
                val viewBl = width.toFloat() / height
                if (bitmapBl > viewBl) {
                    eraserViewWidth = width
                    eraserViewHeight = (eraserViewWidth / bitmapBl).toInt()

                    baseExcursionX = 0f
                    baseExcursionY = (height - eraserViewHeight) / 2f
                } else {
                    eraserViewHeight = height
                    eraserViewWidth = (eraserViewHeight * bitmapBl).toInt()

                    baseExcursionX = (width - eraserViewWidth) / 2f
                    baseExcursionY = 0f
                }
            }

            zoom = INIT_ZOOM // 初始的zoom
            // 偏移量需要限制
            limitExcursion()
        }
    }

    // 设置图片
    fun setBitmap(bitmap: Bitmap) {
        eraserView.bitmap = bitmap
        initEraserViewSize()
    }


    /**
     * 归为，将放大还原
     */
    fun animResetZoom() {
        if (animatorSet?.isRunning == true) { // 如果有动画在运行，则不再执行新的动画
            return
        }
        val animZoom = ValueAnimator.ofFloat(zoom, INIT_ZOOM)
        animZoom.addUpdateListener {
            zoom = it.animatedValue as Float
            limitExcursion() // 限制偏移量
            resetEraserViewLayout() // 刷新橡皮檫控件布局
        }
        val animTop = ValueAnimator.ofFloat(excursionTop, baseExcursionY)
        animTop.addUpdateListener {
            excursionTop = it.animatedValue as Float
        }
        val animLeft = ValueAnimator.ofFloat(excursionLeft, baseExcursionX)
        animLeft.addUpdateListener {
            excursionLeft = it.animatedValue as Float
        }

        animatorSet = AnimatorSet()
        animatorSet?.playTogether(animZoom, animTop, animLeft)
        animatorSet?.duration = ANIM_DURATION
        animatorSet?.start()
    }

    /**
     * 前进方法
     */
    fun advance() {
        eraserView.advance()
    }

    /**
     * 撤销方法
     */
    fun undo() {
        eraserView.undo()
    }

    /**
     * 是否可以前进
     */
    fun isCanAdvance(): Boolean {
        return eraserView.isCanAdvance()
    }

    /**
     * 是否可以撤销
     */
    fun isCanUndo(): Boolean {
        return eraserView.isCanUndo()
    }

    companion object {
        const val INIT_ZOOM = 1f
        const val ANIM_DURATION = 500L
    }
}