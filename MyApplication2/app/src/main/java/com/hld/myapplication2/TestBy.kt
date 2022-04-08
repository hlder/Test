package com.hld.myapplication2

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KProperty


class TestBy {



}


fun main(){
    val testApi:TestApi by TestApiOp()

    println("tostring:${testApi}")
}



//@MainThread
//inline fun <reified VM : ViewModel> testViewModel(
//    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
//): Lazy<VM> {
//    val factoryPromise = factoryProducer ?: {
//        defaultViewModelProviderFactory
//    }
//
//    return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
//}



class TestApiOp{
    private  var testApi:TestApi?=null

    operator fun getValue(thisRef: Any?, property: KProperty<*>):TestApi{
        println("===================1")
        return testApi?:TestApiImpl()
    }
//    operator fun setValue(thisRef: Any?, property: KProperty<*>,value:TestApi){
//        println("===================2")
//
//        testApi=TestApiImpl()
//        testApi?.setTest("zhangsan")
//    }
}


interface TestApi{
    fun setTest(str:String)
}

class TestApiImpl : TestApi{
    private var testStr=""

    override fun setTest(str:String){
        testStr = str
    }

    override fun toString(): String {
        return testStr
    }
}
