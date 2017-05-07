package com.bob.gank_client.http;

import com.bob.gank_client.GankConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bob on 17-5-1.
 * RetrofitClient，使用单例模式返回一个GankRetrofit对象
 */

public class GankRetrofitClient {
        private static GankRetrofit gankRetrofit;
        protected static final Object monitor = new Object();
        private static Retrofit retrofit;

        private GankRetrofitClient(){

        }

        static{
                Gson date_gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
                retrofit = new Retrofit.Builder()
                        .baseUrl(GankConfig.HOST)
                        .addConverterFactory(GsonConverterFactory.create(date_gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
        }

        public static GankRetrofit getGankRetrofitInstance() {
                synchronized (monitor) {
                        if (gankRetrofit == null) {
                                gankRetrofit = retrofit.create(GankRetrofit.class);
                        }
                        return gankRetrofit;
                }
        }

}
