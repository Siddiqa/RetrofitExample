package com.app.retrofitdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.retrofitdemo.R;
import com.app.retrofitdemo.adapter.ViewItemAdapter;

/**
 * Created by admin on 11/17/2016.
 */

public class ViewFragment extends Fragment {
    private RecyclerView mrecycler_view;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.viewfragment,container,false);
        mrecycler_view=(RecyclerView)view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mrecycler_view.setLayoutManager(mLayoutManager);
        ViewItemAdapter adapter=new ViewItemAdapter(getContext(),50);
        mrecycler_view.setAdapter(adapter);

        return view;
    }
}
