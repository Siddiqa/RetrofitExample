package com.app.retrofitdemo.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.retrofitdemo.R;
import com.app.retrofitdemo.utils.AwsomeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by admin on 11/17/2016.
 */

public class Alarmfragment extends Fragment {
    private EditText et_alarm;
    private Button btn_alarm;
    private AwsomeTextView tv_alarm;
    private int mHour,hourOfDay;
    private int mMinute,minute;
    private AlarmManager alarmManager;
    private TextView tv_alarm1;
    int dh,dm;
    private String TAG="Alarmfragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarmfragment, container, false);
        et_alarm = (EditText) view.findViewById(R.id.et_alarm);
        tv_alarm1=(TextView)view.findViewById(R.id.textview_alarm);
        btn_alarm = (Button) view.findViewById(R.id.btn_alarm);
        tv_alarm = (AwsomeTextView) view.findViewById(R.id.tv_alarm);
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        //Date1=new Date();
        alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        tv_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hour,
                                                  int min) {
                                hourOfDay=hour;
                                minute=min;
                                tv_alarm1.setText(hourOfDay + ":" + minute);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        btn_alarm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();

                calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calSet.set(Calendar.MINUTE, minute);
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                long currenttime=System.currentTimeMillis();
                long ct1=calSet.getTimeInMillis();

                if(calSet.compareTo(calNow) <= 0){
                    //Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1);
                   // long ct2=calSet.getTimeInMillis();
                    int dd=hourOfDay;
                    int mm=minute;
                    int d=calNow.get(Calendar.DAY_OF_MONTH);
                    int m=calNow.get(Calendar.MONTH);
                    int m1=m+1;
                    int d1=d+1;

                    String s=d+"-"+m1+"-"+"2016"+" "+dd+":"+mm+":"+"00";
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                    try {
                        String ct=sdf.format(calNow.getTime());
                        Date  Date1 = sdf.parse(s);
                        Date date2=sdf.parse(ct);
                        long diff=Date1.getTime()-date2.getTime();
                        Log.e(TAG," TimeDiffference"+String.valueOf(diff));
                        dm = (int) ((diff / (1000*60)) % 60);
                        dh   = (int) ((diff / (1000*60*60)) % 24);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }






                }
                else
                {
                    int dd=hourOfDay;
                    int mm=minute;
                    int d=calNow.get(Calendar.DAY_OF_MONTH);
                    int m=calNow.get(Calendar.MONTH);
                    int m1=m+1;
                    String s=d+"-"+m1+"-"+"2016"+" "+dd+":"+mm+":"+"00";
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                    try {
                       Date  Date1 = sdf.parse(s);
                        long diff=Date1.getTime()-currenttime;
                        Log.e(TAG," TimeDiffference"+String.valueOf(diff));
                        // long diff=ct1-currenttime;


                        dm = (int) ((diff / (1000*60)) % 60);
                        dh   = (int) ((diff / (1000*60*60)) % 24);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }

                setAlarm(calSet);

            }
        });

        return view;
    }
    private void setAlarm(Calendar targetCal){


        Toast.makeText(getContext(),String.valueOf(dh)+"Hours and "+String.valueOf(dm)+"Minutes to go for Alarm",Toast.LENGTH_SHORT).show();
        Intent i = new Intent("com.atologist.retrofitdemo.ShowDialog");

        /** Creating a Pending Intent */
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);
       // Intent intent = new Intent(getContext(), AlarmReceiver.class);
      //  PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }
}
