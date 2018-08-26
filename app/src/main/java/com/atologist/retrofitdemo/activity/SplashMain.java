package com.app.retrofitdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.app.retrofitdemo.R;

public class SplashMain extends Activity {

    private SharedPreferences sp;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);
        sp = getSharedPreferences("LoginPref", MODE_PRIVATE);
        status = sp.getString("islogin", null);

        if (status != null) {
            if (status.equals("true")) {

                dosplash1();

            } else {

                dosplash();

            }
        } else {

            dosplash();

        }

    }

    public void dosplash() {
        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Intent i = new Intent(SplashMain.this, StirUpMain.class);
                finish();
                startActivity(i);


            }
        }, 3 * 1000);
    }

    public void dosplash1() {
        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Intent intent = new Intent(SplashMain.this, MainActivity.class);
                finish();
                startActivity(intent);

            }
        }, 3 * 1000);
    }
}
