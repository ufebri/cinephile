package com.lagingoding.cinephile.util.base;

import com.lagingoding.cinephile.model.DataTV;

public interface BaseViewTV {

    void showData(DataTV dataTV);

    void showError(String message);

    void hideProgress();

}
