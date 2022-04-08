package com.hld.myapplication2

import android.content.*
import android.os.*
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        findViewById<Button>(R.id.button2).setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val viewModel2:MainViewModel by viewModels()
                viewModel2.doTest()
            }
            viewModel.doTest()
        }




//        val am:ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        am.addAppTask()
//        HashMap;
//        LinkedHashMap;
//        TreeMap;


//        val retrofit:Retrofit=Retrofit.Builder()
////            .callFactory(object: Call.Factory{
////                override fun newCall(request: Request): Call {
////                    return object:Call{
////                        override fun enqueue(responseCallback: Callback) {
////                        }
////                        override fun isExecuted(): Boolean {
////                        }
////                        override fun timeout(): Timeout {
////                        }
////                        override fun clone(): Call {
////                        }
////                        override fun isCanceled(): Boolean {
////                        }
////                        override fun cancel() {
////                        }
////                        override fun request(): Request {
////                        }
////                        override fun execute(): Response {
////                        }
////                    }
////                }
////
////            })
////            .addCallAdapterFactory()
////            .addConverterFactory(object: Converter.Factory() {
////
////            })
//
//            .callFactory(OkHttpClient())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        retrofit.create(TestHttp::class.java).doTest().enqueue()
//
//
//        val okHttpClient=OkHttpClient.Builder().build()
//
//
//        val request=Request.Builder()
//            .url("")
//            .build()
//
//        okHttpClient.newCall(request).enqueue(object :Callback{
//            override fun onFailure(call: Call, e: IOException) {
//
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//
//            }
//
//        })
//        okHttpClient.newCall(request).execute()
//
//        val t:ThreadLocal<String>
//        t.get()
//
//        val handler : Handler
//        handler.obtainMessage()
//        handler.sendMessage()
//        ThreadLocalMap.get
//
//        OkHttpClient client = new OkHttpClient();
//
//        String run(String url) throws IOException {
//            Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//            try (Response response = client.newCall(request).execute()) {
//                return response.body().string();
//            }
//            }

        Testk.a()

        findViewById<Button>(R.id.button).setOnClickListener{

//            ARouter.getInstance().build("/test/activity").navigation()

            println("----------------")
            Executors.newWorkStealingPool()

//            val okHttpClient=OkHttpClient.Builder().build()
//            for(i in 0..100){
//                var request=Request.Builder().url("https://www.wanandroid.com/article/list/0/json?a=$i").build()
//                okHttpClient.newCall(request).execute()
//                okHttpClient.newCall(request).enqueue(object: Callback{
//                    override fun onFailure(call: Call, e: IOException) {
//
//                    }
//                    override fun onResponse(call: Call, response: Response) {
//
//                        var rb=response.body()
//                        println("-----i:$i  rb:${rb}")
//
//                    }
//                })
//
//            }
//            okHttpClient.dispatcher().executorService()


            var retrofit:Retrofit = Retrofit.Builder()
                    .baseUrl("https://www.wanandroid.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()

            var testHttp : TestHttp= retrofit.create(TestHttp::class.java)

            testHttp.doTest().execute()


//
//            var tem:Int= (Math.random()*100).toInt()
//
//            val request=Request.Builder()
//                .url("https://www.wanandroid.com/article/list/0/json?a="+tem)
//                .build()
//            okHttpClient.newCall(request).enqueue(object:Callback{
//                override fun onFailure(call: Call, e: IOException) {
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//
//                    response.body()
//
//                }
//            })


        }


//        Observable.concat()
//
//        Observable.empty<>()
//        Observable.amb()
//        Observable.create()
//
//        Observable.just("").map {
//
//        }.subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe {
//
//        }


    }


    fun b(){

        var tl:ThreadLocal<String>?=null

    }

    fun a(){
        bindService(Intent(),object:ServiceConnection{

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//                var testProxy=Test.Stub.asInterface(service)


            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }


        }, Context.BIND_AUTO_CREATE)

//        Parcel

    }



}