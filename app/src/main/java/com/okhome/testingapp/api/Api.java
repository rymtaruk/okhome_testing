package com.okhome.testingapp.api;

import com.okhome.testingapp.model.CuratedData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    @GET("/v1/curated")
    Single<CuratedData> getCurated(@Query("page") String page);
}
