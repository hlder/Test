package com.hld.myapplication2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    println("===========")
    val scope = CoroutineScope(Dispatchers.IO)
    println("===========1")

    scope.launch {
        println("===========2")
    }
    println("===========3")
}

//
//fun main(){
//    try {
//        TestTest().t(TestTest2())
//    }catch (e:NullPointerException){
//        println("try catch 捕获空指针异常")
//    }
//}
//
//class TestTest{
//    fun t(t2:TestTest2){
//        t2.t2()
//    }
//}
//
//class TestTest2{
//    fun t2(){
//        var a:String? = null
//        a!!.length
//    }
//}