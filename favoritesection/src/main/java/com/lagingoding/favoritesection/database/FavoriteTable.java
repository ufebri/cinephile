package com.lagingoding.favoritesection.database;

import android.provider.BaseColumns;

public class FavoriteTable implements BaseColumns {
    public static String TABLE_MOVIES = "table_movies";
    public static String COLUMN_MOVIE_ID = "_id";
    public static String COLUMN_MOVIE_TITLE = "title";
    public static String COLUMN_POSTER = "poster";
    public static String COLUMN_BACKDROP = "backdrop";
    public static String COLUMN_OVERVIEW = "overview";
    public static String COLUMN_RELEASE_DATE = "release_Date";
    public static String COLUMN_VOTE = "vote";
}
