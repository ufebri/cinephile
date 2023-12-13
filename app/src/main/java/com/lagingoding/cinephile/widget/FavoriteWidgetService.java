package com.lagingoding.cinephile.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoriteWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteWidgetRemoteViewFactory(this.getApplicationContext());
    }
}
