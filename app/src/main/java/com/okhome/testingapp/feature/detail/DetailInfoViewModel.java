package com.okhome.testingapp.feature.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.okhome.testingapp.api.CuratedRepository;
import com.okhome.testingapp.base.BaseViewModel;
import com.okhome.testingapp.model.PhotoData;

import javax.inject.Inject;

public class DetailInfoViewModel extends BaseViewModel {
    private final CuratedRepository repository;

    private final MutableLiveData<PhotoData> photoData = new MutableLiveData<>();

    @Inject
    public DetailInfoViewModel(CuratedRepository repository) {
        this.repository = repository;
    }

    public LiveData<PhotoData> getPhotoData() {
        return photoData;
    }

    public void loadDetailInfo(int id){
        disposable = addDisposable(repository.getDetailCuratedData(id))
                .subscribe(photoData::setValue);
    }
}
