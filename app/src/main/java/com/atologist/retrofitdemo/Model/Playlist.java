package com.app.retrofitdemo.Model;

/**
 * Created by admin on 11/23/2016.
 */

public class Playlist {
    long id;
    String title;
    String path;


    public Playlist(long id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;

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


}
