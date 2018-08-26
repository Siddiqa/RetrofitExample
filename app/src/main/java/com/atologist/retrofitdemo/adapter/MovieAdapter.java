package com.app.retrofitdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.retrofitdemo.Model.Data;
import com.app.retrofitdemo.R;
import com.app.retrofitdemo.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by admin on 11/15/2016.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    public interface MovieAdapterCall{
        void mCallback(Data data);
    }


    private static final String TAG = "MovieAdapter";
    private ArrayList<Data> movielist,filterlist;
    // private Data movielist[];
    private Context context;
    MovieAdapterCall movieAdapterCall;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading;
    private MainActivity mainActivity;
    public OnItemCLickListener mItemClickListener;

    public MovieAdapter(ArrayList<Data> movielist, Context context, MainActivity mainActivity, MovieAdapterCall movieAdapterCall) {
        Log.e(TAG, "MovieAdapter: " + movielist.size());
        this.movielist = movielist;
        this.context = context;
        this.mainActivity=mainActivity;
        this.movieAdapterCall= movieAdapterCall;
        filterlist=new ArrayList<>();
        this.filterlist.addAll(movielist);

    }



    @Override
    public MovieViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_item, parent, false);
        return new MovieViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        for (Data data : movielist) {
            Log.e(TAG, "onBindViewHolder: " + data.getId() );
        }


        MovieViewHolder holder1=(MovieViewHolder)holder;
        holder1.t_title.setText(movielist.get(position).getTitle());
        holder1.t_viewcount.setText(movielist.get(position).getViewCount()+ " " + "views");
        holder1.t_timestamp.setText(movielist.get(position).getPublishedAt());
        Picasso.with(context).load(movielist.get(position).getThumbnails()).into(holder1.img_thumbnail);

        holder.img_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieAdapterCall.mCallback(movielist.get(position));


                /*
                String transname=null;
                MovieDetailfragment endFragment = new MovieDetailfragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    transname=holder.img_thumbnail.getTransitionName();
                    f1.setSharedElementReturnTransition(TransitionInflater.from(
                            mainActivity).inflateTransition(R.transition.change_image_transform));
                    f1.setExitTransition(TransitionInflater.from(mainActivity).inflateTransition(android.R.transition.fade));

                    endFragment.setSharedElementEnterTransition(TransitionInflater.from(mainActivity).inflateTransition(R.transition.change_image_transform));
                    endFragment.setEnterTransition(TransitionInflater.from(mainActivity).inflateTransition(android.R.transition.fade));
                }

                Bundle bundle = new Bundle();
                bundle.putString("ACTION",movielist.get(position).getThumbnails());

                endFragment.setArguments(bundle);
                FragmentManager fragmentManager =mainActivity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, endFragment)
                        .addToBackStack("Payment")
                        .addSharedElement(holder.img_thumbnail,transname)
                        .commit();*/
            }
        });
    }



//    @Override
//    public int getItemViewType(int position) {
//        return movielist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
//    }


    @Override
    public int getItemCount() {
        return (null != filterlist ? filterlist.size() : 0);
    }

    public  class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView img_thumbnail;
        private final TextView t_title;
        private final TextView t_viewcount;
        private final TextView t_timestamp;

        public MovieViewHolder(View itemView) {
            super(itemView);
            img_thumbnail = (ImageView) itemView.findViewById(R.id.imgv_thumbnail);
            t_title = (TextView) itemView.findViewById(R.id.tv_title);
            t_viewcount = (TextView) itemView.findViewById(R.id.tv_viewcount);
            t_timestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);


        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getPosition(), img_thumbnail,movielist.get(getPosition()).getThumbnails());
            }
        }
    }

    public void clearAdapter()
    {
        movielist.clear();

    }
    public String getImageTransitionName(Context context, int position) {
        return context.getString(R.string.imagetransition) + position;
    }

    public interface OnItemCLickListener {
        void onItemClick(int position,ImageView imageView,String imgurl);
    }

    public void setOnItemClickListener(final OnItemCLickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterlist.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterlist.addAll(movielist);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (Data item : movielist) {
                        if (item.getTitle().toLowerCase().contains(text.toLowerCase()) ) {
                            // Adding Matched items
                            filterlist.add(item);
                            Log.e(TAG," Inside for each adding items "+ item);
                        }
                    }
                }

                // Set on UI Thread
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }
}
