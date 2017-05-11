package com.bob.gank_client.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bob.gank_client.GankConfig;
import com.bob.gank_client.mvp.model.entity.BaseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by bob on 17-5-6.
 */

public  class SharePrefrenceUtil <T extends  BaseModel>{
        //TODO 考虑采用Builder模式
        private static final String GANK = "gank";
        private static final String FIRST_OPEN = "is_first_open";
        private static Gson gson = new Gson();

        public   boolean saveData(Context context, List<T> list, String type) {
                SharedPreferences preferences = context.getSharedPreferences(GANK, context.MODE_PRIVATE);
                return preferences.edit().putString(type, gson.toJson(list)).commit();
        }

        public  List<T> getData(Context context,String type) {
                SharedPreferences preferences = context.getSharedPreferences(GANK, context.MODE_PRIVATE);
                return gson.fromJson(preferences.getString(type, ""), new TypeToken<List<T>>() {
                }.getType());
        }

        public  static void setChrome(Context context) {
                SharedPreferences preferences = context.getSharedPreferences(GANK, context.MODE_PRIVATE);
                preferences.edit().putBoolean(GankConfig.CHROME, true).commit();
        }

        public static boolean getChrome(Context context) {
                SharedPreferences preferences = context.getSharedPreferences(GANK, context.MODE_PRIVATE);
                return preferences.getBoolean(GankConfig.CHROME,true);
        }





}
