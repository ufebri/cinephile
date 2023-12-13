package com.lagingoding.cinephile.util.base;

import com.lagingoding.cinephile.model.DataMovies;
import com.lagingoding.cinephile.model.DataTV;

public interface BaseViewMT {

    void showDataTv(DataTV dataTV);

    void showDataMovie(DataMovies dataMovies);

    void showErrorMessage(String message);

    void hideProgress();
}
