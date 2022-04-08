package com.hld.myapplication2

import android.content.Context
import android.view.MotionEvent
import android.widget.LinearLayout

class TestView(context: Context?) : LinearLayout(context) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun postInvalidate() {
        super.postInvalidate()
    }
}