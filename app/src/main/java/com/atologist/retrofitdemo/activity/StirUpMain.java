
package com.app.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.app.retrofitdemo.R;
import com.app.retrofitdemo.fragment.LoginFragment;
import com.app.retrofitdemo.fragment.RegisterFragment;
import com.app.retrofitdemo.fragment.SplashFragment;
import com.itsronald.widget.ViewPagerIndicator;


public class StirUpMain extends AppCompatActivity {

    private ViewPager stirup_viewpager;

    private Button btn_register;
    private Button btn_sign;
    private ViewPagerIndicator vindicator;
    private LoginFragment login;
    private RegisterFragment register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stir_up_main);
        stirup_viewpager = (ViewPager) findViewById(R.id.stirupmain_viewpager);
        vindicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        btn_register = (Button) findViewById(R.id.btnRegister);
        btn_sign = (Button) findViewById(R.id.btnSignIn);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewpageadapter viewpageadapter=new viewpageadapter(getSupportFragmentManager());
        stirup_viewpager.setAdapter(viewpageadapter);
        login=new LoginFragment();
        register=new RegisterFragment();
        stirup_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==5)
                {
                    // Add second fragment by replacing first

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_stir_up_main, login);
                    // Apply the transaction
                    ft.commit();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_stir_up_main, login);
                // Apply the transaction
                ft.commit();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_stir_up_main, register);
                // Apply the transaction
                ft.commit();
            }
        });

    }

    class viewpageadapter extends FragmentStatePagerAdapter {

        public viewpageadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SplashFragment.newInstance("splash1");

                case 1:
                    return SplashFragment.newInstance("splash2");

                case 2:
                    return SplashFragment.newInstance("splash3");

                case 3:
                    return SplashFragment.newInstance("splash4");

                case 4:
                    return SplashFragment.newInstance("splash5");
                case 5:
                    return SplashFragment.newInstance("splash1");


                default:
                    return SplashFragment.newInstance("splash1");

            }


        }

        @Override
        public int getCount() {
            return 6;
        }
    }

    public void setRegFragment(RegisterFragment register){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_stir_up_main, register);
        // Apply the transaction
        ft.commit();
    }
    public void setLogFragment(LoginFragment register){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_stir_up_main, register);
        // Apply the transaction
        ft.commit();
    }


}
