package com.hld.myapplication2

import java.lang.NullPointerException

fun main(){
    try {
        TestTest().t(TestTest2())
    }catch (e:NullPointerException){
        println("try catch 捕获空指针异常")
    }
}

class TestTest{
    fun t(t2:TestTest2){
        t2.t2()
    }
}

class TestTest2{
    fun t2(){
        var a:String? = null
        a!!.length
    }
}