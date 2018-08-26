package com.app.retrofitdemo;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by admin on 11/17/2016.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
