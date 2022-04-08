package com.hld.myapplication2

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){


    fun doTest(){
        println("=====dotest")
    }

    override fun onCleared() {
        super.onCleared()
        println("-----onmainCleared")
    }

}