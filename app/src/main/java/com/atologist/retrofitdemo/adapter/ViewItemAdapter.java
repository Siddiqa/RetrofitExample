package com.app.retrofitdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.retrofitdemo.Model.ViewModel;
import com.app.retrofitdemo.R;
import com.app.retrofitdemo.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by admin on 11/17/2016.
 */

public class ViewItemAdapter extends RecyclerView.Adapter<ViewItemAdapter.ViewItemHolder> {

    private Context context;
    private int count;
    private  MainActivity mainActivity;
    ViewModel viewModel= new ViewModel();
    ArrayList<ViewModel> viewModelArrayList=new ArrayList<>();

    public ViewItemAdapter(Context context, int count) {
        this.context = context;
        this.count = count;
        //this.mainActivity=mainActivity;
    }

    @Override
    public ViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_layout, parent, false);

        return new ViewItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewItemHolder holder, int position) {

        holder.btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn=holder.et_fn.getText().toString();
                String ln=holder.et_ln.getText().toString();
//                ViewModel vm=new ViewModel();
//                vm.setFirstname(fn);
//                vm.setLastname(ln);
                viewModel.setFirstname(fn);
                viewModel.setLastname(ln);
                viewModelArrayList.add(viewModel);

                if(viewModel!=null){
                    ((MainActivity)context).craeteTab(viewModelArrayList);
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class ViewItemHolder extends RecyclerView.ViewHolder {

        private final EditText et_fn;
        private final EditText et_ln;
        private final Button btn_submit;

        public ViewItemHolder(View itemView) {
            super(itemView);
            et_fn = (EditText) itemView.findViewById(R.id.viewitem_et_fn);
            et_ln = (EditText) itemView.findViewById(R.id.viewitem_et_ln);
            btn_submit = (Button) itemView.findViewById(R.id.viewitem_btn_submit);

        }
    }
}
