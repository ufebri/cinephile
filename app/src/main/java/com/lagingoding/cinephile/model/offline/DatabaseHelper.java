package com.lagingoding.cinephile.model.offline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.lagingoding.cinephile.model.offline.DataColumns.TABLE_MOVIES;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "DatabaseMovies";
    private static final int DATABASE_VERSION = 7;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE " + TABLE_MOVIES + " (" +
                DataColumns.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataColumns.COLUMN_POSTER + " TEXT, " +
                DataColumns.COLUMN_BACKDROP + " TEXT, " +
                DataColumns.COLUMN_MOVIE_TITLE + " TEXT, " +
                DataColumns.COLUMN_OVERVIEW + " TEXT, " +
                DataColumns.COLUMN_RELEASE_DATE + " TEXT, " +
                DataColumns.COLUMN_VOTE + " TEXT " +
                ");";

        db.execSQL(SQL_CREATE_TABLE_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }
}