package com.okhome.testingapp.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class BaseViewModel extends ViewModel {
    protected final CompositeDisposable subscribe = new CompositeDisposable();
    protected Disposable disposable = new CompositeDisposable();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingMore = new MutableLiveData<>();
    private int loadingCounter = 0;
    private int moreCounter = 0;

    public LiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    public LiveData<Boolean> getLoadingMore() {
        return loadingMore;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @NonNull
    protected <T> Maybe<T> showLoading(Maybe<T> source) {
        return source.doOnSubscribe(d -> {
            synchronized (loadingState) {
                if (loadingCounter == 0) {
                    loadingState.setValue(true);
                }
                loadingCounter++;
            }
        }).doAfterTerminate(() -> {
            synchronized (loadingState) {
                loadingCounter--;
                if (loadingCounter == 0) {
                    loadingState.setValue(false);
                }
            }
        });
    }

    @NonNull
    protected <T> Maybe<T> moreLoading(Maybe<T> source) {
        return source.doOnSubscribe(d -> {
            synchronized (loadingMore) {
                if (moreCounter == 0) {
                    loadingMore.setValue(true);
                }
                moreCounter++;
            }
        }).doAfterTerminate(() -> {
            synchronized (loadingMore) {
                moreCounter--;
                if (moreCounter == 0) {
                    loadingMore.setValue(false);
                }
            }
        });
    }

    protected boolean errorHandler(Throwable e) {
        errorMessage.setValue(e.getMessage());
        return true;
    }

    protected <T> Maybe<T> addDisposable(@NonNull Single<T> repository) {
        return repository.toMaybe().subscribeOn(Schedulers.io())
                .delay(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .compose(this::showLoading)
                .onErrorComplete(this::errorHandler)
                .compose(this::disposeOnClear);
    }

    @Override
    protected void onCleared() {
        subscribe.clear();
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onCleared();
    }

    protected void disposeOnClear(Disposable disposables) {
        subscribe.add(disposables);
    }

    @NonNull
    protected <T> Maybe<T> disposeOnClear(Maybe<T> source) {
        return source.doOnSubscribe(this::disposeOnClear);
    }

}
