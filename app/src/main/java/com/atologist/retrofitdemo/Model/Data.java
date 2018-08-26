package com.app.retrofitdemo.Model;

/**
 * Created by admin on 11/15/2016.
 */

public class Data {
    private String publishedAt;

    private String id;

    private String title;

    private String category_id;

    private String thumbnails;

    private String youtube_id;

    private String viewCount;

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "ClassPojo [publishedAt = " + publishedAt + ", id = " + id + ", title = " + title + ", category_id = " + category_id + ", thumbnails = " + thumbnails + ", youtube_id = " + youtube_id + ", viewCount = " + viewCount + "]";
    }
}
