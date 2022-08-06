package com.hld.myapplication2.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class ZoomViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val zoom = ViewZoomController(this)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when {
            event.action == MotionEvent.ACTION_DOWN -> { // 单指按下
                zoom.touchDown(event.rawX, event.rawY)
            }
            (event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN -> { // 多指按下
                zoom.touchDown2(
                    event.getX(0),
                    event.getY(0),
                    event.getX(1),
                    event.getY(1)
                )
            }
            event.action == MotionEvent.ACTION_MOVE -> { // 移动
                zoom.touchMove(event)
            }
            event.action == MotionEvent.ACTION_UP -> { // 手指抬起
                zoom.touchUp(event.rawX, event.rawY)
            }
            event.action == MotionEvent.ACTION_CANCEL -> { // 手指取消
                zoom.touchCancel(event.rawX, event.rawY)
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}