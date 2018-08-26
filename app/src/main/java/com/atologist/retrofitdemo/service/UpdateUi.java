package com.app.retrofitdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.retrofitdemo.fragment.VideoFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 11/16/2016.
 */

public class UpdateUi extends Service {
    static int count=1;

    private static final String TAG = "UpdateUi";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.e(TAG," Inside Service ");
        Timer t=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Log.e(TAG," Inside timer ");
                Intent broadcastintent=new Intent();
                broadcastintent.setAction(VideoFragment.UPDATE);
                broadcastintent.putExtra("Countvalue",count);
                sendBroadcast(broadcastintent);
                count++;
                if(count>=5)
                {
                    count=1;
                }
            }
        };
       // t.scheduleAtFixedRate(timerTask,0,300000);
        t.scheduleAtFixedRate(timerTask,0,30000);
        return super.onStartCommand(intent, flags, startId);
    }
}
