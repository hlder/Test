package com.hld.myapplication2

import retrofit2.Call
import retrofit2.http.GET

interface TestHttp {

    @GET(value = "/article/list/0/json")
    fun doTest():Call<String>

}