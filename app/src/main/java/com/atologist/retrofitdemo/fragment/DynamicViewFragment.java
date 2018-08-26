package com.app.retrofitdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.retrofitdemo.Model.ViewModel;
import com.app.retrofitdemo.R;

import java.util.ArrayList;

/**
 * Created by admin on 11/17/2016.
 */

public class DynamicViewFragment extends Fragment {
    private LinearLayout llmain;
    private ArrayList<ViewModel> viewModel = new ArrayList<>();
    private LinearLayout.LayoutParams lparams;

    public DynamicViewFragment(ArrayList<ViewModel> viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamicviewfragment, container, false);
        llmain = (LinearLayout) view.findViewById(R.id.linearmain);
        // viewModel= (ArrayList<ViewModel>) getArguments().getSerializable("model");
        lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < viewModel.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(lparams);
            tv.setText(viewModel.get(i).getFirstname());

            TextView tv1 = new TextView(getContext());
            tv1.setLayoutParams(lparams);
            tv1.setText(viewModel.get(i).getLastname());
            this.llmain.addView(tv);
            this.llmain.addView(tv1);
        }


        return view;
    }

    public void setData(ArrayList<ViewModel> viewModel) {
        for (int i = 0; i < viewModel.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(lparams);
            tv.setText(viewModel.get(i).getFirstname());

            TextView tv1 = new TextView(getContext());
            tv1.setLayoutParams(lparams);
            tv1.setText(viewModel.get(i).getLastname());
            this.llmain.addView(tv);
            this.llmain.addView(tv1);
        }
    }
}
