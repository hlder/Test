package com.hld.mvvm.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * 橡皮檫，擦除控件
 */
class EraserView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    View(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    /**
     * 画笔的颜色
     */
    var paintColor = Color.BLUE
        set(value) {
            field = value
            downPointPaint.color = value
            pathPaint.color = value
        }

    /**
     * 画笔的粗细
     */
    var paintStrokeWidth = 50f
        set(value) {
            field = value
            pathPaint.strokeWidth = value
        }

    /**
     * 画笔的透明度
     */
    var paintAlpha = 100
        set(value) {
            field = value
            pathPaint.alpha = value
        }

    /**
     * 橡皮檫后的颜色
     */
    var eraserColor: Int = Color.WHITE
        set(value) {
            field = value
        }

    /**
     * 背景图片，需要擦拭的图片
     */
    var bitmap: Bitmap? = null
        set(value) {
            field = value
            value?.let {
                bitmapSrcRect = Rect(0, 0, it.width, it.height)
                eraserBitmap?.recycle() // 如果之前存在，主动销毁上一个bitmap
                eraserBitmap = Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_4444)
            }
        }

    /**
     * 缩放倍数
     */
    var zoom: Float = 1f

    private var bitmapSrcRect: Rect? = null
    private var bitmapDstRect: Rect = Rect(0, 0, 0, 0)

    private var eraserBitmap: Bitmap? = null
        set(value) {
            field = value
            value?.let {
                eraserBitmapCanvas = Canvas(it)
            }
        }
    private var eraserBitmapCanvas: Canvas? = null

    private val bitmapPaint = Paint()

    private val listPath = ArrayList<EraserPath>()
    private val undoListPath = ArrayList<EraserPath>()

    private var downPoint: PointF? = null
    private val downPointPaint by lazy {
        Paint().apply {
            color = paintColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    // 橡皮擦的圆形paint
    private val eraserCirclePaint by lazy {
        Paint().apply {
            color = eraserColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    private var touchDownPath: Path? = null // 按下去赋值，离开置空，主要用于比例换算
    private val path = Path()
    private val pathPaint = Paint().apply {
        strokeWidth = paintStrokeWidth
        color = paintColor
        style = Paint.Style.STROKE
        isAntiAlias = true
        alpha = paintAlpha
    }

//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//        if (w > 0 && h > 0 && w != oldw && h != oldh) {
//            if(eraserBitmap==null){
//                eraserBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
//            }
//        }
//    }

    /**
     * 是否可以前进
     */
    fun isCanAdvance(): Boolean {
        return undoListPath.size > 0
    }

    /**
     * 是否可以撤销
     */
    fun isCanUndo(): Boolean {
        return listPath.size > 0
    }

    /**
     * 前进方法
     */
    fun advance() {
        if (undoListPath.size > 0) {
            listPath.add(undoListPath.removeAt(undoListPath.size - 1))
            resetEraserCanvas()
        }
    }

    /**
     * 撤销方法
     */
    fun undo() {
        if (listPath.size > 0) {
            undoListPath.add(listPath.removeAt(listPath.size - 1))
            resetEraserCanvas()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        bitmapDstRect.right = width
        bitmapDstRect.bottom = height
        val src = bitmapSrcRect ?: return

        bitmap?.let {
            canvas?.drawBitmap(it, src, bitmapDstRect, bitmapPaint)
        }

        eraserBitmap?.let {
            canvas?.drawBitmap(it, src, bitmapDstRect, bitmapPaint)
        }
        canvas?.drawPath(path, pathPaint)
        downPoint?.let {
            canvas?.drawCircle(it.x, it.y, paintStrokeWidth / 2f, downPointPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val blx = eraserBitmap?.let {
            it.width.toFloat() / width
        } ?: return true
        val bly = eraserBitmap?.let {
            it.height.toFloat() / height
        } ?: return true

        if (event?.action == MotionEvent.ACTION_DOWN) {
            touchDownPath = Path()
            path.moveTo(event.x, event.y)
            touchDownPath?.moveTo(event.x * blx, event.y * bly)
            downPoint = PointF(event.x, event.y)
        } else if (event?.action == MotionEvent.ACTION_MOVE) {
            path.lineTo(event.x, event.y)
            touchDownPath?.lineTo(event.x * blx, event.y * bly)
            invalidate()
        } else if (event?.action == MotionEvent.ACTION_UP || event?.action == MotionEvent.ACTION_CANCEL) {
            if (event.action == MotionEvent.ACTION_UP) {
                touchDownPath?.let {
                    val paint = Paint(pathPaint)
                    paint.strokeWidth = blx * paintStrokeWidth
                    paint.color = eraserColor
                    val dPoint = downPoint?.let { dp ->
                        PointF(dp.x * blx, dp.y * bly)
                    }
                    val uPoint = PointF(event.x * blx, event.y * bly)
                    listPath.add(EraserPath(it, paint, dPoint, uPoint))
                }
                undoListPath.clear()
            }
            path.reset()
            downPoint = null
            touchDownPath = null
            resetEraserCanvas()
        }
        return true
    }

    /**
     * 改变橡皮檫画布的内容,重新绘制橡皮檫画布
     */
    private fun resetEraserCanvas() {
        eraserBitmapCanvas?.let {
            it.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR) // 清空画布
            listPath.forEach { item ->
                // 绘制线条
                it.drawPath(item.path, item.paint)
                // 绘制初始点的圆，让线条更圆润
                item.downPoint?.let { dp ->
                    it.drawCircle(dp.x, dp.y, item.paint.strokeWidth / 2f, eraserCirclePaint)
                }
                // 绘制结束点的圆，让线条更圆润
                it.drawCircle(item.upPoint.x,item.upPoint.y,item.paint.strokeWidth / 2f, eraserCirclePaint)
            }
            invalidate()
        }
    }
}