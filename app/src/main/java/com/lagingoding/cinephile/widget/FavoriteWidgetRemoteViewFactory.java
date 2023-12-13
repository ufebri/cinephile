package com.lagingoding.cinephile.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.lagingoding.cinephile.BuildConfig;
import com.lagingoding.cinephile.R;
import com.lagingoding.cinephile.model.remote.Movie;

import java.util.concurrent.ExecutionException;

import static com.lagingoding.cinephile.model.offline.DatabaseContract.CONTENT_URI;

public class FavoriteWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;

    FavoriteWidgetRemoteViewFactory(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie movie = getFavorite(position);
        String linkImage = movie.getPosterPath();
        Bitmap bitmap = null;

        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(BuildConfig.IMAGE_LINK_W500 + linkImage)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.item_favorite_widget);
        remoteViews.setImageViewBitmap(R.id.iv_item_favorite_widget, bitmap);
        remoteViews.setTextViewText(R.id.tv_item_favorite_widget, movie.getOriginalTitle());

        Bundle extra = new Bundle();
        extra.putLong(FavoriteWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(extra);

        remoteViews.setOnClickFillInIntent(R.id.iv_item_favorite_widget, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Movie getFavorite(int position) {
        cursor.moveToPosition(position);
        return new Movie(cursor);
    }
}
