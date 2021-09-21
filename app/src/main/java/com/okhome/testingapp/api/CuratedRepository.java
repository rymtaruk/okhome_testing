package com.okhome.testingapp.api;

import javax.inject.Inject;

public class CuratedRepository {
    private final Api api;

    @Inject
    public CuratedRepository(Api api) {
        this.api = api;
    }
}
