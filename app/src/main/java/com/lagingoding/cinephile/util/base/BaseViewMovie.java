package com.lagingoding.cinephile.util.base;

import com.lagingoding.cinephile.model.DataMovies;

public interface BaseViewMovie {

    void showData(DataMovies dataMovies);

    void showErrorMessage(String message);

    void hideProgress();

}