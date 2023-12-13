package com.lagingoding.cinephile.ui.main.tv;

import com.lagingoding.cinephile.BuildConfig;
import com.lagingoding.cinephile.model.DataTV;
import com.lagingoding.cinephile.util.base.BaseViewTV;
import com.lagingoding.cinephile.util.network.NetworkClient;
import com.lagingoding.cinephile.util.network.NetworkService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TvPresenter implements TvView {

    private BaseViewTV baseViewInterface;

    public TvPresenter(BaseViewTV baseViewInterface) {
        this.baseViewInterface = baseViewInterface;
    }

    @Override
    public void getTvShow() {
        getObservableTVShow().subscribe(getObserver());
    }

    private Observable<DataTV> getObservableTVShow() {
        return NetworkClient.getData().create(NetworkService.class)
                .getTVPopular(BuildConfig.API_KEY, "en-EN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<DataTV> getObserver() {
        return new DisposableObserver<DataTV>() {
            @Override
            public void onNext(DataTV dataTV) {
                baseViewInterface.showData(dataTV);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                baseViewInterface.showError(e.getMessage());
            }

            @Override
            public void onComplete() {
                baseViewInterface.hideProgress();
            }
        };
    }
}
