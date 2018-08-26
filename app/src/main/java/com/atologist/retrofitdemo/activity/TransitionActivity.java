package com.app.retrofitdemo.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.retrofitdemo.R;
import com.app.retrofitdemo.fragment.MovieDetailfragment;
import com.app.retrofitdemo.utils.DetailsTransition;

public class TransitionActivity extends BaseActivity{
    private FrameLayout transitionContainer;
    private ImageView transtionImgv;
    private TextView transtionTv;
//    private ImageView imageView;
    private Bundle bundle;
    private LinearLayout view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_transition);
        initComponents();

        setListner();
    }

    @Override
    void initComponents() {
        transitionContainer = (FrameLayout)findViewById( R.id.transition_container );
        transtionImgv = (ImageView)findViewById( R.id.transtion_imgv );
        transtionTv = (TextView)findViewById( R.id.transtion_tv );
        view1=(LinearLayout)findViewById(R.id.view1);
    //    imageView = (ImageView)findViewById( R.id.imageView );
        bundle=new Bundle();
    }

    @Override
    void setListner() {
        view1.setOnClickListener(new View.OnClickListener() {
            public String textTransitionName;

            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


                    ImageView staticImage = (ImageView) findViewById(R.id.transtion_imgv);

                    MovieDetailfragment detailfragment=new MovieDetailfragment();

                    String imageTransitionName=transtionImgv.getTransitionName();
                    textTransitionName = transtionTv.getTransitionName();
                    bundle.putString("TRANS_NAME", imageTransitionName);
                    bundle.putString("ACTION", transtionTv.getText().toString());
                    bundle.putString("TRANS_TEXT", textTransitionName);
                    bundle.putParcelable("IMAGE", ((BitmapDrawable) transtionImgv.getDrawable()).getBitmap());
                    detailfragment.setArguments(bundle);


                    detailfragment.setSharedElementEnterTransition(new DetailsTransition());
                    detailfragment.setEnterTransition(new Fade());
                    getWindow(). setExitTransition(new Fade());
                    detailfragment.setSharedElementReturnTransition(new DetailsTransition());

                    // Add second fragment by replacing first
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                            .replace(R.id.transition_container, detailfragment)
                            .addToBackStack("Payment")
                            .addSharedElement(transtionImgv, "userPic")
                            .addSharedElement(transtionTv, "userName")
                            .addSharedElement(staticImage, getString(R.string.fragment_image_trans));
                    // Apply the transaction
                    ft.commit();
                } else {
                    // Code to run on older devices
                }
            }
        });
    }

    @Override
    void prepairView() {

    }

    @Override
    void callWebService() {

    }
}
