package com.lagingoding.cinephile.ui.search;

import com.lagingoding.cinephile.BuildConfig;
import com.lagingoding.cinephile.model.DataMovies;
import com.lagingoding.cinephile.model.DataTV;
import com.lagingoding.cinephile.util.base.BaseViewMT;
import com.lagingoding.cinephile.util.network.NetworkClient;
import com.lagingoding.cinephile.util.network.NetworkService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchView {

    private BaseViewMT baseViewSearch;

    public SearchPresenter(BaseViewMT baseViewSearch) {
        this.baseViewSearch = baseViewSearch;
    }

    @Override
    public void getResultSearchMovie(String query) {
        getObservableResultSearchMovie(query).subscribe(getObserverMovie());
    }

    @Override
    public void getResultSearchTv(String query) {
        getObservableResultSearchTv(query).subscribe(getObserverTv());
    }

    public Observable<DataMovies> getObservableResultSearchMovie(String query) {
        return NetworkClient.getData().create(NetworkService.class)
                .getResultMovie(BuildConfig.API_KEY, "id-ID", query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<DataMovies> getObserverMovie() {
        return new DisposableObserver<DataMovies>() {
            @Override
            public void onNext(DataMovies dataMovies) {
                baseViewSearch.showDataMovie(dataMovies);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                baseViewSearch.showErrorMessage(e.getMessage());
            }

            @Override
            public void onComplete() {
                baseViewSearch.hideProgress();
            }
        };
    }

    public Observable<DataTV> getObservableResultSearchTv(String query) {
        return NetworkClient.getData().create(NetworkService.class)
                .getResultTv(BuildConfig.API_KEY, "en-EN", query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<DataTV> getObserverTv() {
        return new DisposableObserver<DataTV>() {
            @Override
            public void onNext(DataTV dataTV) {
                baseViewSearch.showDataTv(dataTV);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                baseViewSearch.showErrorMessage(e.getMessage());
            }

            @Override
            public void onComplete() {
                baseViewSearch.hideProgress();
            }
        };
    }
}
