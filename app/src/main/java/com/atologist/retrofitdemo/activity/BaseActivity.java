package com.app.retrofitdemo.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 11/18/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    abstract void initComponents();
    abstract void setListner();
    abstract void prepairView();
    abstract void callWebService();
}
