package com.app.retrofitdemo.utils;

/**
 * Created by admin on 11/22/2016.
 */
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class MusicRetriever {
    final String TAG = "MusicRetriever";
    ContentResolver mContentResolver;
    Context context;
    // the items (songs) we have queried
    List<Item> mItems = new ArrayList<Item>();
    Random mRandom = new Random();
    public MusicRetriever(ContentResolver cr, Context ct) {
        mContentResolver = cr;
        context=ct;
    }
    /**
     * Loads music data. This method may take long, so be sure to call it asynchronously without
     * blocking the main thread.
     */
    public ArrayList<Item> prepare() {
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Log.e(TAG, "Querying media...");
        Log.e(TAG, "URI: " + uri.toString());
        // Perform a query on the content resolver. The URI we're passing specifies that we
        // want to query for all audio media on external storage (e.g. SD card)
        Cursor cur = mContentResolver.query(uri, null,
                MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);
        Log.e(TAG, "Query finished. " + (cur == null ? "Returned NULL." : "Returned a cursor."));
        if (cur == null) {
            // Query failed...
            Log.e(TAG, "Failed to retrieve music: cursor is null :-(");
            return null;
        }
        if (!cur.moveToFirst()) {
            // Nothing to query. There is no music on the device. How boring.
            Log.e(TAG, "Failed to move cursor to first row (no query results).");
            return null;
        }
        Log.e(TAG, "Listing...");
        // retrieve the indices of the columns where the ID, title, etc. of the song are

        int titleColumn = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int idColumn = cur.getColumnIndex(MediaStore.Audio.Media._ID);
        int  pathid = cur.getColumnIndex(MediaStore.Audio.Media.DATA);
        Long albumId = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
        Log.e(TAG, "Title column index: " + String.valueOf(titleColumn));
        Log.e(TAG, "ID column index: " + String.valueOf(titleColumn));
        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);



        ArrayList<Item> itemlist=new ArrayList<>();
        // add each song to mItems
        do {
            Log.e(TAG, "ID: " + cur.getString(idColumn) + " Title: " + cur.getString(titleColumn));


            Item i1=new Item(cur.getLong(idColumn), cur.getString(titleColumn), cur.getString(pathid));
            itemlist.add(i1);
        } while (cur.moveToNext());
        Log.e(TAG, "Done querying media. MusicRetriever is ready.");
        cur.close();
        return itemlist;
    }

    /** Returns a random Item. If there are no items available, returns null. */
    public Item getRandomItem() {
        if (mItems.size() <= 0) return null;
        return mItems.get(mRandom.nextInt(mItems.size()));
    }
    public static class Item {
        long id;

        String title;
        String path;
        Bitmap bm;

        public Item(long id, String title,String path) {
            this.id = id;
            this.title = title;
            this.path=path;


        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Bitmap getBm() {
            return bm;
        }

        public void setBm(Bitmap bm) {
            this.bm = bm;
        }
    }
}
