package com.app.retrofitdemo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.retrofitdemo.Model.Data;
import com.app.retrofitdemo.Model.ResponseModel;
import com.app.retrofitdemo.R;
import com.app.retrofitdemo.Rest.ApiClient;
import com.app.retrofitdemo.Rest.ApiInterface;
import com.app.retrofitdemo.adapter.MovieAdapter;
import com.app.retrofitdemo.interfaces.OnLoadMoreListener;
import com.app.retrofitdemo.utils.ConnectionDetector;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import com.genxhippies.library.RippleVisibleImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 11/15/2016.
 */

public class VideoFragment extends BaseFragment implements MovieAdapter.OnItemCLickListener {


    private static final String TAG = "VideoFragment";
    private final static String API_TYPE = "android";
    private final static String API_ACEESSKEY = "ea23d3a9a900a80b2f8dd326bf8bb6c4";
    private final static String API_SECRETKEY = "fb17603c34a03916e226c5ed831ee19asasas";
    private final static String TYPE = "Home";
    private final static String PAGINATION = "1";
    public static RecyclerView recyclerview;
    private ProgressBar progressbar;

    private MovieAdapter movieAdapter;
    public static final String UPDATE = "updateui";
    private IntentFilter mIntentFilter;
    public boolean isRegistered;
    LinearLayoutManager mLayoutManager;

    MovieAdapter.MovieAdapterCall movieAdapterCall;
    private VideoAdapter mvideoadapter;
    ArrayList<Data> mData = new ArrayList<>();
    private int page = 2;
    private MaterialRefreshLayout materialRefreshLayout;
    private View online_layout;
    private View offline_layout;

//    public VideoFragment(MainActivity mainActivity) {
//        this.mainActivity = mainActivity;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videofragment, container, false);
        recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        online_layout=(View)view.findViewById(R.id.onlinelayout);
        offline_layout=(View)view.findViewById(R.id.offlinelayout);
        // mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManager);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(UPDATE);


        movieAdapterCall = new MovieAdapter.MovieAdapterCall() {
            @Override
            public void mCallback(Data data) {


            }
        };
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();

                        callapi(1);
                    }
                }, 3000);
                materialRefreshLayout.finishRefreshLoadMore();

            }

            @Override
            public void onfinish() {

            }

        });
        //materialRefreshLayout.setWaveColor(0xffffffff);
        materialRefreshLayout.setIsOverLay(true);
        materialRefreshLayout.setWaveShow(true);
        // Call<ResponseModel<Data>> getMovie = apiService.getMovies(API_TYPE, API_ACEESSKEY, API_SECRETKEY, TYPE, PAGINATION);


//        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPullToRefreshView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        mPullToRefreshView.setRefreshing(false);
//                        movieAdapter.clearAdapter();
//                        callapi(1);
//                    }
//                }, 2000);
//            }
//        });

//
        ConnectionDetector c1=new ConnectionDetector();
        if(c1.isNetworkAvailable(getContext()))
        {
            online_layout.setVisibility(View.VISIBLE);
            offline_layout.setVisibility(View.GONE);
            callapi(1);
        }
        else
        {
            online_layout.setVisibility(View.GONE);
            offline_layout.setVisibility(View.VISIBLE);
        }

        offline_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);

//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
//                startActivity(intent);
            }
        });
        return view;
    }

    private void callapi(int c) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel<Data>> getMovie = apiService.getMovies(API_TYPE, API_ACEESSKEY, API_SECRETKEY, TYPE, String.valueOf(c));

        Log.e(TAG, "onCreateView: " + getMovie.request().url());
        getMovie.enqueue(new Callback<ResponseModel<Data>>() {
            @Override
            public void onResponse(Call<ResponseModel<Data>> call, Response<ResponseModel<Data>> response) {
                progressbar.setVisibility(View.GONE);

                Log.e(TAG, "onResponse: ");

//                movieAdapter = new MovieAdapter(response.body().getData(), getContext(), mainActivity, movieAdapterCall);
//
//                movieAdapter.setOnItemClickListener(VideoFragment.this);
//                recyclerview.setAdapter(movieAdapter);
                addData(response.body().getData());

            }

            @Override
            public void onFailure(Call<ResponseModel<Data>> call, Throwable t) {
                Log.e(TAG, "onFailure: ");

            }
        });
    }

    private void addData(final ArrayList<Data> yData) {

        Log.e(TAG, "addData: yData :" + yData.size());
        if (mData != null) {
            mData.clear();
        }
        mData.addAll(yData);


        if (mvideoadapter == null) {
            mvideoadapter = new VideoAdapter(mData);
            recyclerview.setAdapter(mvideoadapter);
            mvideoadapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mData.add(null);
                            mvideoadapter.notifyItemInserted(mData.size() - 1);
                        }
                    });
                    //Load more data for reyclerview
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Remove loading item
                            mData.remove(mData.size() - 1);
                            mvideoadapter.notifyItemRemoved(mData.size());
                            getNewData();

                        }
                    }, 3000);
                }
            });
        } else {
            recyclerview.setAdapter(mvideoadapter);
            mvideoadapter.notifyDataSetChanged();
        }


    }

    public void getNewData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel<Data>> getMovie = apiService.getMoviesbypost(API_TYPE, API_ACEESSKEY, API_SECRETKEY, TYPE, String.valueOf(page));

        Log.e(TAG, "onCreateView: " + getMovie.request().url());
        getMovie.enqueue(new Callback<ResponseModel<Data>>() {
            @Override
            public void onResponse(Call<ResponseModel<Data>> call, Response<ResponseModel<Data>> response) {
                progressbar.setVisibility(View.GONE);
                Log.e("REsponse", response.message());
                mData.addAll(response.body().getData());
                mvideoadapter.notifyDataSetChanged();
                mvideoadapter.setLoaded();
                page++;

            }

            @Override
            public void onFailure(Call<ResponseModel<Data>> call, Throwable t) {

            }
        });

    }

    public BroadcastReceiver mreciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(UPDATE)) {
                //   Toast.makeText(getContext(),"Boradcastrecieved",Toast.LENGTH_LONG).show();
                progressbar.setVisibility(View.VISIBLE);
                int c = intent.getIntExtra("Countvalue", 0);
                callapi(c);


            }

        }
    };

    @Override
    public void onDetach() {
        super.onDetach();

//        Intent intent = new Intent(getContext(), UpdateUi.class);
//        getContext().stopService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.e(TAG, "OnResume");
//        register();
//        Intent intent = new Intent(getContext(), UpdateUi.class);
//        getContext().startService(intent);

    }

    public void unregister() {
        if (isRegistered) {
            getContext().unregisterReceiver(mreciever);
            isRegistered = false;
            Log.e(TAG, "Unregister Method: inside if Register true");
        } else {
            Log.e(TAG, "Unregister Method: inside else Register false");

        }

    }

    public void register() {
        getContext().registerReceiver(mreciever, mIntentFilter);
        isRegistered = true;
    }


    @Override
    void initComponents() {

    }

    @Override
    void setListner() {

    }

    @Override
    void prepairView() {

    }

    public void Search(String text) {
        movieAdapter.filter(text);
    }

    @Override
    public void onItemClick(int position, ImageView imageView, String iimgurl) {
//        MovieDetailfragment fragment = MovieDetailfragment.getInstance(getActivity(), movieAdapter.getImageTransitionName(getActivity(), position), iimgurl);
//        getFragmentManager().beginTransaction()
//                .addSharedElement(imageView, movieAdapter.getImageTransitionName(getActivity(), position))
//                .replace(R.id.container, fragment)
//                .addToBackStack(null)
//                .commit();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private final RippleVisibleImageView img_thumbnail;
        private final TextView t_title;
        private final TextView t_viewcount;
        private final TextView t_timestamp;


        public VideoViewHolder(View itemView) {
            super(itemView);

            img_thumbnail = (RippleVisibleImageView) itemView.findViewById(R.id.imgv_thumbnail);
            t_title = (TextView) itemView.findViewById(R.id.tv_title);
            t_viewcount = (TextView) itemView.findViewById(R.id.tv_viewcount);
            t_timestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progresbar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progresbar = (ProgressBar) itemView.findViewById(R.id.progressBar_loading);
        }
    }

    class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;
        private OnLoadMoreListener mOnLoadMoreListener;
        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        private ArrayList<Data> mData;

        public VideoAdapter(ArrayList<Data> mData) {
            this.mData = mData;
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
            recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
        }

        public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
            this.mOnLoadMoreListener = mOnLoadMoreListener;
        }

        @Override
        public int getItemViewType(int position) {
            return mData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_item, parent, false);
                return new VideoViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VideoViewHolder) {
                VideoViewHolder holder1 = (VideoViewHolder) holder;
                holder1.t_title.setText(mData.get(position).getTitle());
                holder1.t_viewcount.setText(mData.get(position).getViewCount() + " " + "views");
                String es = calculatediff(mData.get(position).getPublishedAt());
                holder1.t_timestamp.setText(es);
                Picasso.with(getContext()).load(mData.get(position).getThumbnails()).into(holder1.img_thumbnail);


                ((VideoViewHolder) holder).img_thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progresbar.setIndeterminate(true);
            }
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        public void setLoaded() {
            isLoading = false;}

        public void clearadapter() {
            mData.clear();
        }

        public String calculatediff(String publishedat) {
            //  String t="2016-11-22T09:09:54.000Z";
            String elapsedtime = null;
            Calendar calNow = Calendar.getInstance();
            String t1[] = publishedat.split("T");
            String dd = t1[0];
            String dd2 = t1[1];
            String tme[] = dd2.split(".000Z");
            String tim = tme[0];
            String publishedtime = dd + " " + tim;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String ct = sdf.format(calNow.getTime());
            try {
                Date date1 = sdf.parse(publishedtime);
                Date date2 = sdf.parse(ct);
                long difference = date2.getTime() - date1.getTime();
                long seconds = ((difference / 1000 )% 60);
                long minuttes = ((difference / (1000 * 60)) % 60);
                long hours = ((difference / (1000 * 60 * 60)) % 60);
                long days=((difference/(1000*60*60*60)%24));
                long weeks=((difference/(1000*60*60*60*24)%7));
                long months=((difference/(1000*60*60*6024*7)%31));

                

                    // time in month

                    if (months == 0) {

                        // time in weeks

                        if (weeks == 0) {

                            // time in days

                            if (days == 0) {

                                // time in hours

                                if (hours == 0) {
                                    // time in minute

                                    if (minuttes == 0) {
                                        // time in seconds

                                        if (seconds == 0) {
                                            // just now
                                            elapsedtime = "Just Now";
                                        } else {
                                            // in second
                                            if (seconds == 1)
                                                elapsedtime = seconds + " Second ago";
                                            else
                                                elapsedtime = seconds + " Seconds ago";
                                        }
                                    } else {
                                        // in minute

                                        if (minuttes == 1)
                                            elapsedtime = minuttes + " Minute ago";
                                        else
                                            elapsedtime = minuttes + " Minutes ago";
                                    }
                                } else {
                                    // in hour

                                    if (hours == 1)
                                        elapsedtime = hours + " Hour ago";
                                    else
                                        elapsedtime = hours + " Hours ago";
                                }

                            } else {

                                // in days

                                if (days == 1)
                                    elapsedtime = days + " Day ago";
                                else
                                    elapsedtime = days + " Days ago";
                            }
                        } else {

                            // in weeks

                            if (weeks == 1)
                                elapsedtime = weeks + " Week ago";
                            else
                                elapsedtime = weeks + " Weeks ago";
                        }

                    } else {

                        // in months

                        if (months == 1)
                            elapsedtime = months + " Month ago";
                        else
                            elapsedtime = months + " Months ago";
                    }
               






            } catch (ParseException e) {
                e.printStackTrace();
            }

            return elapsedtime;
        }
    }


}
