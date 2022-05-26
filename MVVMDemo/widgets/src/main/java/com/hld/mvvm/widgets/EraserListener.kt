package com.hld.mvvm.widgets

/**
 * 擦除监听
 */
interface EraserListener {
    // 擦除
    fun onErasure()
    // 撤销
    fun onUndo()
    // 前进
    fun onaAdvance()
    // 清理所有path
    fun onClearPath()
}