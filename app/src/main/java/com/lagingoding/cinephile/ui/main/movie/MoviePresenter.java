package com.lagingoding.cinephile.ui.main.movie;

import com.lagingoding.cinephile.BuildConfig;
import com.lagingoding.cinephile.model.DataMovies;
import com.lagingoding.cinephile.util.base.BaseViewMovie;
import com.lagingoding.cinephile.util.network.NetworkClient;
import com.lagingoding.cinephile.util.network.NetworkService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviePresenter implements MovieView {

    private BaseViewMovie baseViewInterface;

    public MoviePresenter(BaseViewMovie baseViewInterface) {
        this.baseViewInterface = baseViewInterface;
    }

    @Override
    public void getNowPlaying() {
        getObservableNowPlaying().subscribe(getObserver());
    }

    private Observable<DataMovies> getObservableNowPlaying() {
        return NetworkClient.getData().create(NetworkService.class)
                .getNowPlaying(BuildConfig.API_KEY, "en-EN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private DisposableObserver<DataMovies> getObserver() {
        return new DisposableObserver<DataMovies>() {
            @Override
            public void onNext(DataMovies dataMovies) {
                baseViewInterface.showData(dataMovies);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                baseViewInterface.showErrorMessage(e.getMessage());
            }

            @Override
            public void onComplete() {
                baseViewInterface.hideProgress();
            }
        };
    }
}
