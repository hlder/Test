package com.hld.mvvm.widgets

import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF

data class EraserPath(
    val path: Path,
    val paint: Paint,
    val downPoint: PointF?,
    val upPoint: PointF
)
