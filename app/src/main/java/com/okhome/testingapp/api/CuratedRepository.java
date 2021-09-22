package com.okhome.testingapp.api;

import com.okhome.testingapp.model.CuratedData;
import com.okhome.testingapp.model.PhotoData;

import javax.inject.Inject;

import io.reactivex.Single;

public class CuratedRepository {
    private final Api api;

    @Inject
    public CuratedRepository(Api api) {
        this.api = api;
    }

    public Single<CuratedData> getCuratedData(String page){
        return this.api.getCurated(page);
    }

    public Single<PhotoData> getDetailCuratedData(int id){
        return this.api.getDetailCurated(id);
    }
}
