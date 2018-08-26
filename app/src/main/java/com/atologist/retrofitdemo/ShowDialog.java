package com.app.retrofitdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by admin on 11/17/2016.
 */

public class ShowDialog extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Creating an Alert Dialog Window */
        AlertDemo alert = new AlertDemo();

        /** Opening the Alert Dialog Window. This will be opened when the alarm goes off */
        alert.show(getSupportFragmentManager(), "AlertDemo");
    }
}
