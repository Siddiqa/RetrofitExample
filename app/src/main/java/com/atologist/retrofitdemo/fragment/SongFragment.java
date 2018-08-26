package com.app.retrofitdemo.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.retrofitdemo.R;
import com.app.retrofitdemo.adapter.SongAdapter;
import com.app.retrofitdemo.utils.CustomLog;
import com.app.retrofitdemo.utils.MusicRetriever;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by admin on 11/15/2016.
 */

public class SongFragment extends Fragment {
    private final static String[] acceptedExtensions = {"mp3", "mp2", "wav", "flac", "ogg", "au", "snd", "mid", "midi", "kar"
            , "mga", "aif", "aiff", "aifc", "m3u", "oga", "spx"};
    String selection = MediaStore.Audio.Media.DATA + " like ?";
    String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME};
    Cursor cursor2;
    //your database elect statement
    String selection2 = MediaStore.Audio.Media.IS_MUSIC + " != 0";
    //your projection statement
    String[] projection2 = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID
    };
    private RecyclerView recyclerview_song;
    private ProgressBar progressbar_song;
    private RecyclerView recyclerview_Song;
    private String mediapath = new String(Environment.getExternalStorageDirectory().getAbsolutePath());
    private int STORAGE_PERMISSION_CODE = 10;
    private File file;
    private ArrayList<String> myList;
    private ArrayList<String> album_path;
    private ArrayList<Bitmap> bm_list;
    private String TAG = "SongFragment";
    private Handler mHandler;
    private File musicFolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songfragment, container, false);
        recyclerview_song = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressbar_song = (ProgressBar) view.findViewById(R.id.progressbar);
        recyclerview_song.setLayoutManager(new LinearLayoutManager(getContext()));
        myList = new ArrayList<String>();
        bm_list = new ArrayList<>();
        album_path = new ArrayList<String>();
        mHandler = new Handler();
        musicFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                // We have access. Life is good.
                new getaudio().execute();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // We've been denied once before. Explain why we need the permission, then ask again.
                //  getActivity().showDialog(DIALOG_PERMISSION_REASON);
            } else {

                // We've never asked. Just do it.
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            }
        }


        return view;
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(CustomLog.getTAG(SongFragment.this), "OnRequestPermissionMethod");
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(CustomLog.getTAG(SongFragment.this), "OnRequestPermissionMethod Inside if");
                //Displaying a toast
                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
                new getaudio().execute();
            } else {
                Log.e(CustomLog.getTAG(SongFragment.this), "OnRequestPermissionMethod Inside else");
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    class getaudio extends AsyncTask<Void, Void, Void> {
        ArrayList<MusicRetriever.Item> items;

        @Override
        protected Void doInBackground(Void... voids) {
            ContentResolver contentResolver = getContext().getContentResolver();
            MusicRetriever musicRetriever = new MusicRetriever(contentResolver, getContext());
            items = musicRetriever.prepare();



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressbar_song.setVisibility(View.GONE);


                    SongAdapter sd = new SongAdapter(items, getContext());
                    recyclerview_song.setAdapter(sd);

                }
            });

        }
    }
}
