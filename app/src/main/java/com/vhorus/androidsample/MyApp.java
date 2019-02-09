package com.vhorus.androidsample;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApp extends Application{
    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        MySettings.initDB();
    }

    public static synchronized MyApp getInstance() {
        return mInstance;
    }

    public static SharedPreferences getShardPrefs(){
        SharedPreferences prefs = mInstance.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        return prefs;
    }
}
