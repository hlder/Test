package com.hld.myapplication2;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.OkHttpClient;

class Person{
    String name;
    int age;
    int sex;

    int temperature;//温度
}

//公园
class PublicPark{

    List<Person> list = new LinkedList<>();

    int inManRoomNum=0;
    int inWomanRoomNum=0;

    //让男女一对一进入
    public void in(Person person){
        if(person.sex==0){
            inManRoomNum++;
        }else{
            inWomanRoomNum++;
        }



        list.add(person);
    }

    void load(ClassLoader classLoader){


        LinkedHashMap linkedHashMap=new LinkedHashMap();
        Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(5);
        Executors.newSingleThreadScheduledExecutor();
        Executors.newWorkStealingPool();
        Executors.newScheduledThreadPool(5);

    }

    public void out(Person person){
        list.remove(person);
    }

}

interface TestI{

}

class TestImpl implements TestI{

}


class InTask implements Runnable{
    PublicPark publicPark;
    InTask(PublicPark publicPark){
        this.publicPark=publicPark;
    }
    @Override
    public void run() {

    }
}
class OutTask implements Runnable{
    PublicPark publicPark;
    OutTask(PublicPark publicPark){
        this.publicPark=publicPark;
    }
    @Override
    public void run() {

    }
}


public class Test2 {
    public static void main(String[] args) {

        PublicPark publicPark = new PublicPark();

        Runnable in=new InTask(publicPark);
        Runnable out=new OutTask(publicPark);

        new Thread(in).start();
        new Thread(out).start();




    }
}
