package com.app.retrofitdemo.fragment;

/**
 * Created by admin on 11/18/2016.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {

    abstract void initComponents();
    abstract void setListner();
    abstract void prepairView();

}
