package com.app.retrofitdemo.Rest;


import com.app.retrofitdemo.Model.Data;
import com.app.retrofitdemo.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by admin on 11/15/2016.
 */

public interface ApiInterface {
    @GET("get_videos.php?")
    Call<ResponseModel<Data>> getMovies(@Query("api_type") String apitype, @Query("api_access_key ") String api_access_key, @Query("api_secret") String api_secret, @Query("type") String type, @Query("pagination") String pagination);

    @FormUrlEncoded
    @POST("get_videos.php?")
    Call<ResponseModel<Data>> getMoviesbypost(@Field("api_type") String apitype, @Field("api_access_key") String api_access_key, @Field("api_secret") String api_secret, @Field("type") String type, @Field("pagination") String pagination);
}
