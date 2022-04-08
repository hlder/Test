package com.hld.gameflyingsword

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class GameView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }
}