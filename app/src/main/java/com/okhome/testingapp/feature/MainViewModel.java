package com.okhome.testingapp.feature;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.okhome.testingapp.api.CuratedRepository;
import com.okhome.testingapp.base.BaseViewModel;
import com.okhome.testingapp.model.CuratedData;
import com.okhome.testingapp.model.PhotoData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {
    private final CuratedRepository repository;

    private final MutableLiveData<List<PhotoData>> photoData = new MutableLiveData<>();
    private int page = 1;

    @Inject
    public MainViewModel(CuratedRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<PhotoData>> getPhotoData() {
        return photoData;
    }

    @SuppressLint("CheckResult")
    public void loadCurated() {
        String strPage = String.valueOf(page);
        disposable = addDisposable(repository.getCuratedData(strPage))
                .subscribe(this::onResponse);
    }

    public void loadMore() {
        page += 1;
        String strPage = String.valueOf(page);
        disposable = repository.getCuratedData(strPage)
                .toMaybe()
                .subscribeOn(Schedulers.io())
                .delay(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .compose(this::moreLoading)
                .onErrorComplete(this::errorHandler)
                .compose(this::disposeOnClear)
                .subscribe(this::onResponse);
    }

    private void onResponse(CuratedData data){
        photoData.setValue(data.getPhotos());
    }
}
