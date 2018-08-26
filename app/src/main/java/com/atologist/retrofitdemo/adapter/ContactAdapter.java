package com.app.retrofitdemo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.retrofitdemo.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;

/**
 * Created by admin on 11/19/2016.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewholder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<String> name_list;
    private ArrayList<String> number_list;
    private ArrayList<String> filterlist;
    private String TAG="ContactAdapter";
    private Fragment fragment;

    public ContactAdapter(Context context, ArrayList<String> name_list, ArrayList<String> number_list, Fragment fragment) {
        this.context = context;
        this.name_list = name_list;
        this.number_list = number_list;
        this.fragment=fragment;
        filterlist=new ArrayList<>();
        filterlist.addAll(name_list);
    }

    @Override
    public ContactViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_list,parent,false);
        return new ContactViewholder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewholder holder, int position) {
        holder.tv_name.setText(filterlist.get(position));
        holder.tv_number.setText(number_list.get(position));

    }

    @Override
    public long getHeaderId(int position) {
        if (position == -1) {
            return -1;
        } else {
            return filterlist.get(position).charAt(0);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contactview_header,parent,false);
        return new RecyclerView.ViewHolder(view){};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(String.valueOf(filterlist.get(position).charAt(0)));
    }



    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    class ContactViewholder extends RecyclerView.ViewHolder{

        private final TextView tv_number;
        private final TextView tv_name;

        public ContactViewholder(View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.contact_tv_name);
            tv_number=(TextView)itemView.findViewById(R.id.contact_tv_number);
        }
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

                    filterlist.addAll(name_list);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (String item : name_list) {
                        if (item.toLowerCase().contains(text.toLowerCase()) ) {
                            // Adding Matched items
                            filterlist.add(item);
                            Log.e(TAG," Inside for each adding items "+ item);
                        }
                    }
                }
                fragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });


            }
        }).start();

    }
}
