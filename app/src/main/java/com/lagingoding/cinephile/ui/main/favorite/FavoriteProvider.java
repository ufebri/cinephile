package com.lagingoding.cinephile.ui.main.favorite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lagingoding.cinephile.model.offline.DatabaseContract;
import com.lagingoding.cinephile.model.offline.DataColumns;
import com.lagingoding.cinephile.model.offline.MovieHelper;

import java.util.Objects;

import static com.lagingoding.cinephile.model.offline.DatabaseContract.CONTENT_URI;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(DatabaseContract.AUTHORITY, DataColumns.TABLE_MOVIES, FAVORITE);
        mUriMatcher.addURI(DatabaseContract.AUTHORITY, DataColumns.TABLE_MOVIES + "/#", FAVORITE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (mUriMatcher.match(uri)) {
            case FAVORITE:
                cursor = movieHelper.queryProviderMovie();
                break;
            case FAVORITE_ID:
                cursor = movieHelper.queryByIDProviderMovie(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (mUriMatcher.match(uri)) {
            case FAVORITE:
                added = movieHelper.inserProviderMovie(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (mUriMatcher.match(uri)) {
            case FAVORITE_ID:
                deleted = movieHelper.deleteProviderMovie(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (mUriMatcher.match(uri)) {
            case FAVORITE_ID:
                updated = movieHelper.updateProviderMovie(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
