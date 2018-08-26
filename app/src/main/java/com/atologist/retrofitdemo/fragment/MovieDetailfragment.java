package com.app.retrofitdemo.fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.retrofitdemo.R;

/**
 * Created by admin on 11/18/2016.
 */

public class MovieDetailfragment extends Fragment {
    private String imagestring;
    private static final String ARG_TRANSITION_NAME = "ARG_TRANSITION_NAME";
    private static final String ARG_IMAGE_REF_ID = "ARG_IMAGE_REF_ID";








//    public static MovieDetailfragment getInstance(Context context, String transitionName, String imgurl) {
//        MovieDetailfragment fragment = new MovieDetailfragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(ARG_TRANSITION_NAME, transitionName);
//        bundle.putString(ARG_IMAGE_REF_ID, imgurl);
//        fragment.setSharedElementEnterTransition(TransitionInflater.from(context).inflateTransition(R.transition.change_image_transform));
//        fragment.setEnterTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.fade));
//        fragment.setExitTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.fade));
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.moviedetail,container,false);
        Bundle bundle = getArguments();
        String actionTitle = "DUMMY ACTION";
        Bitmap imageBitmap = null;
        String transText = "";
        String transitionName = "";

        if (bundle != null) {
            transitionName = bundle.getString("TRANS_NAME");
            actionTitle = bundle.getString("ACTION");
            imageBitmap = bundle.getParcelable("IMAGE");
            transText = bundle.getString("TRANS_TEXT");
        }
        ImageView imageView=(ImageView)view.findViewById(R.id.target_imgv);
        TextView  textView=(TextView)view.findViewById(R.id.target_tv);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView .setTransitionName(transitionName);
            textView.setTransitionName(transText);
        }

        imageView .setImageBitmap(imageBitmap);
        textView.setText(actionTitle);
//        imageView.setTransitionName(getArguments().getString(ARG_TRANSITION_NAME));
//        imagestring=getArguments().getString(ARG_IMAGE_REF_ID);
//        Picasso.with(getContext()).load(imagestring).into(imageView);
        return view;
    }
}
