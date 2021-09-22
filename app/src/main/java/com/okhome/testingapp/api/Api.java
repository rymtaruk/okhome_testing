package com.okhome.testingapp.api;

import com.okhome.testingapp.model.CuratedData;
import com.okhome.testingapp.model.PhotoData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("/v1/curated")
    Single<CuratedData> getCurated(@Query("page") String page);

    @GET("/v1/photos/{id}")
    Single<PhotoData> getDetailCurated(@Path("id") int id);
}
