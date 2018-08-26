package com.app.retrofitdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.retrofitdemo.R;

/**
 * Created by admin on 11/21/2016.
 */

public class SplashFragment extends Fragment {

    private View view;

    public static SplashFragment newInstance(String view)
    {
        SplashFragment f1=new SplashFragment();
        Bundle args=new Bundle();
        args.putString("Type",view);
        f1.setArguments(args);
        return f1;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String viewtype=getArguments().getString("Type");
        if(viewtype.equals("splash1"))
        {
            view=inflater.inflate(R.layout.splash1,container,false);

        }
        else if(viewtype.equals("splash2"))
        {
            view=inflater.inflate(R.layout.splash2,container,false);
        }
        else if(viewtype.equals("splash3"))
        {
            view=inflater.inflate(R.layout.splash3,container,false);
        }
        else if(viewtype.equals("splash4"))
        {
            view=inflater.inflate(R.layout.splash4,container,false);
        }
        else if(viewtype.equals("splash5"))
        {
            view=inflater.inflate(R.layout.splash5,container,false);
        }
        return view;
    }

}
