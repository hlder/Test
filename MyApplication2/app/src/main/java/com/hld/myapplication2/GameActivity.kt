package com.hld.myapplication2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hld.gameflyingsword.GameView

class GameActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GameView(this))
    }
}