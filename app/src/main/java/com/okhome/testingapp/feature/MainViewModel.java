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
    private final MutableLiveData<List<PhotoData>> onRefresh = new MutableLiveData<>();
    private int page = 1;
    private int perPage = 0;

    @Inject
    public MainViewModel(CuratedRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<PhotoData>> getPhotoData() {
        return photoData;
    }

    public LiveData<List<PhotoData>> getDataOnRefresh() {
        return onRefresh;
    }

    @SuppressLint("CheckResult")
    public void loadCurated() {
        page += 1;

        String strPage = String.valueOf(page);
        String strPerPage = String.valueOf(15);

        disposable = addDisposable(repository.getCuratedData(strPage, strPerPage))
                .subscribe(this::onResponse);
    }

    public void loadMore() {
        page += 1;

        String strPage = String.valueOf(page);
        String strPerPage = String.valueOf(15);

        disposable = repository.getCuratedData(strPage, strPerPage)
                .toMaybe()
                .subscribeOn(Schedulers.io())
                .delay(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .compose(this::moreLoading)
                .onErrorComplete(this::errorHandler)
                .compose(this::disposeOnClear)
                .subscribe(this::onResponse);
    }

    public void loadOnRefresh(){
        page = 1;
        String strPage = String.valueOf(page);
        String strPerPage = String.valueOf(perPage);

        if (perPage == 0){
            loadCurated();
        } else {
            disposable = addDisposable(repository.getCuratedData(strPage, strPerPage))
                    .subscribe(this::onRefreshResponse);
        }
    }

    private void onResponse(CuratedData data){
        perPage += data.getPerPage();
        photoData.setValue(data.getPhotos());
    }

    private void onRefreshResponse(CuratedData data){
        onRefresh.setValue(data.getPhotos());
    }


}
