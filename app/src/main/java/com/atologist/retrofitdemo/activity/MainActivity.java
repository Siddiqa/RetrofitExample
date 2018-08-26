package com.app.retrofitdemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.app.retrofitdemo.Model.ViewModel;
import com.app.retrofitdemo.R;
import com.app.retrofitdemo.fragment.Alarmfragment;
import com.app.retrofitdemo.fragment.ContactFragment;
import com.app.retrofitdemo.fragment.DynamicViewFragment;
import com.app.retrofitdemo.fragment.SongFragment;
import com.app.retrofitdemo.fragment.VideoFragment;
import com.app.retrofitdemo.fragment.ViewFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends DrawerActivity {

    private TabLayout tablayout;
    private ViewPager viewpager;
    private View item2;
    private View item1;
    private VideoFragment f1;
    private SongFragment f2;
    private ViewFragment f3;
    private DynamicViewFragment f4;
    private Alarmfragment f5;
    private ContactFragment f6;
    private ViewpagerAdapter adapter;
    public final List<String> mmFragmentTitleList = new ArrayList<>();
    public static final List<Fragment> FragmentList = new ArrayList<>();
    private String TAG = "MAINACTIVTY";
    private View item3;

    private MenuItem searchMenuItem;
    private MenuItem attachment_menuitem;
    boolean hidden=true;
    LinearLayout mRevealView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tablayout = (TabLayout) findViewById(R.id.htab_tabs);
        viewpager = (ViewPager) findViewById(R.id.htab_viewpager);
        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);
        mRevealView.setVisibility(View.INVISIBLE);
        item1 = (View) findViewById(R.id.item1);
        item2 = (View) findViewById(R.id.item2);
        item3 = (View) findViewById(R.id.item3);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tablayout.setupWithViewPager(viewpager);

        f1 = new VideoFragment();
        f2 = new SongFragment();
        f3 = new ViewFragment();
        f5 = new Alarmfragment();
        f6 = new ContactFragment();

        adapter = new ViewpagerAdapter(getSupportFragmentManager());
        setupViewPager(viewpager);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(5);
       // invalidateOptionsMenu();
        Boolean isTrue = true;
        isTrue = !isTrue;
        Log.e(TAG, "Boolean" + isTrue);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public static final String TAG = "MainActivity";

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==3)
                {
                    searchMenuItem.setVisible(true);
                }
                else
                {
                    searchMenuItem.setVisible(false);
                    searchMenuItem.collapseActionView();
                }

                //using broadcast reciever updating ui


                if (position == 1 || position == 2 || position == 3 || position == 4 || position == 5) {

                    Log.e(TAG, "onPageSelected: unregister Brodcast done");
                    f1.unregister();

                } else if (position == 0) {
                    f1.register();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawer(GravityCompat.START);
                viewpager.setCurrentItem(0);

            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawer(GravityCompat.START);
                viewpager.setCurrentItem(1);
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                startActivity(intent);


            }
        });

    }

    private void setupViewPager(ViewPager viewpager) {

        adapter.addFragment(f1, "TRENDING");
        adapter.addFragment(f2, "SONGS");
        adapter.addFragment(f5, "ALARM");
        adapter.addFragment(f6, "CONTACT");
        adapter.addFragment(f3, "Views");
        adapter.notifyDataSetChanged();


    }

    class ViewpagerAdapter extends FragmentStatePagerAdapter {


        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mmFragmentTitleList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mmFragmentTitleList.add(title);
            FragmentList.add(fragment);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mmFragmentTitleList.get(position);
        }
    }

    boolean isFirst = false;


    public void craeteTab(ArrayList<ViewModel> modelArrayList) {
        if (!isFirst) {
            f4 = new DynamicViewFragment(modelArrayList);

            isFirst = true;
            Log.e(TAG, "creating tabs");

            adapter.addFragment(f4, "Dynamic Views");
            adapter.notifyDataSetChanged();
            if (adapter.getCount() > 3)
                viewpager.setCurrentItem(5);

        } else {

            f4.setData(modelArrayList);
            if (adapter.getCount() > 3)
                viewpager.setCurrentItem(5);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        attachment_menuitem=menu.findItem(R.id.action_atachment);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchMenuItem);

        searchView.setQueryHint("Search Here");

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                f6.filter(query);
                hideKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        if(id==R.id.action_atachment)
        {
            // finding X and Y co-ordinates
            int cx = (mRevealView.getLeft() + mRevealView.getRight());
            int cy = (mRevealView.getTop());

            // to find  radius when icon is tapped for showing layout
            int startradius=0;
            int endradius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

            // performing circular reveal when icon will be tapped
            Animator animator = ViewAnimationUtils.createCircularReveal(mRevealView,cx, cy, startradius, endradius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(400);

            //reverse animation
            // to find radius when icon is tapped again for hiding layout
            //  starting radius will be the radius or the extent to which circular reveal animation is to be shown

            int reverse_startradius = Math.max(mRevealView.getWidth(),mRevealView.getHeight());

            //endradius will be zero
            int reverse_endradius=0;

            // performing circular reveal for reverse animation
            Animator animate = ViewAnimationUtils.createCircularReveal(mRevealView,cx,cy,reverse_startradius,reverse_endradius);
            if(hidden){

                // to show the layout when icon is tapped
                mRevealView.setVisibility(View.VISIBLE);
                animator.start();
                hidden = false;
            }
            else {
                mRevealView.setVisibility(View.VISIBLE);

                // to hide layout on animation end
                animate.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }
                });
                animate.start();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        List<Fragment> allFragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : allFragments) {
            if (fragment instanceof Alarmfragment) {

                viewpager.setCurrentItem(0);
                Log.e(TAG, "inside if");
                break;

            } else {
                super.onBackPressed();
                Log.e(TAG, "inside else");
                break;
            }
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

