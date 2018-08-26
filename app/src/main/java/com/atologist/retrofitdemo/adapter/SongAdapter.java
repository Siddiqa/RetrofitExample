package com.app.retrofitdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.retrofitdemo.Model.Playlist;
import com.app.retrofitdemo.R;
import com.app.retrofitdemo.activity.AndroidBuildingMusicPlayerActivity;
import com.app.retrofitdemo.utils.MusicRetriever;
import com.app.retrofitdemo.utils.Utilities;

import java.util.ArrayList;

/**
 * Created by admin on 11/15/2016.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongviewHolder> {
    private Context context;
    ArrayList<MusicRetriever.Item> itemArrayList;
    private Bitmap bm;
    public static ArrayList<Playlist> playlistArrayList = new ArrayList<Playlist>();

    public SongAdapter(ArrayList<MusicRetriever.Item> items, Context context) {
        this.itemArrayList = items;
        this.context = context;
    }

    @Override
    public SongviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);

        return new SongviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SongviewHolder holder, final int position) {


        holder.tv_songname.setText(itemArrayList.get(position).getTitle());
        //Logger.debug(albumArtUri.toString());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(itemArrayList.get(position).getPath());
        byte[] artBytes = mmr.getEmbeddedPicture();
        if (artBytes != null) {
            //     InputStream is = new ByteArrayInputStream(mmr.getEmbeddedPicture());
            bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
            holder.img_somg.setImageBitmap(bm);
        } else {
            bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            holder.img_somg.setImageBitmap(bm);
        }
        // holder.img_somg.setImageBitmap(itemArrayList.get(position).getBm());

        holder.tv_songname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AndroidBuildingMusicPlayerActivity.class);
                intent.putExtra("Index", position);
//                intent.putExtra("Path", itemArrayList.get(position).getPath());
//                intent.putExtra("Title", itemArrayList.get(position).getTitle());
                context.startActivity(intent);
            }
        });

        holder.img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayPopupWindow(holder.img_menu, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class SongviewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_songname;
        private final ImageView img_somg;
        private final ImageView img_menu;

        public SongviewHolder(View itemView) {
            super(itemView);
            tv_songname = (TextView) itemView.findViewById(R.id.tv_songtitle);
            img_somg = (ImageView) itemView.findViewById(R.id.img_songicon);
            img_menu = (ImageView) itemView.findViewById(R.id.img_menu);

        }
    }

    private void displayPopupWindow(View anchorView, final int position) {
        final PopupWindow popup = new PopupWindow(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.pop_up, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        TextView playnow = (TextView) layout.findViewById(R.id.tv_playnow);
        TextView addtoplaylist = (TextView) layout.findViewById(R.id.tv_addtoplaylist);

        playnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AndroidBuildingMusicPlayerActivity.class);
                intent.putExtra("Index", position);
                popup.dismiss();
                context.startActivity(intent);
            }
        });

        addtoplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utilities.isActive()) {
                    Playlist playlist = new Playlist(itemArrayList.get(position).getId(), itemArrayList.get(position).getTitle(), itemArrayList.get(position).getPath());
                    playlistArrayList.add(playlist);
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                    popup.dismiss();
                }
            }
        });


        // Show anchored to button

        popup.showAsDropDown(anchorView);
    }

}
