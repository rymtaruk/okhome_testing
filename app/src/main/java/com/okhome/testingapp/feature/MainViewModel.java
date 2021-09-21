package com.okhome.testingapp.feature;

import com.okhome.testingapp.api.CuratedRepository;
import com.okhome.testingapp.base.BaseViewModel;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel {
    private final CuratedRepository repository;

    @Inject
    public MainViewModel(CuratedRepository repository) {
        this.repository = repository;
    }
}
