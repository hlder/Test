package com.hld.myapplication2

import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        println("=======onviewModel")
    }

}