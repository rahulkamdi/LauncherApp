package com.android.applib;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static MyApp myApp;

    public static Context getContext() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

}
