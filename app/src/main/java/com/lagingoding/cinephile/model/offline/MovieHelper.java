package com.lagingoding.cinephile.model.offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;

public class MovieHelper {
    private static String DATABASE_TABLE = DataColumns.TABLE_MOVIES;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor queryByIDProviderMovie(String id) {
        return database.query(DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProviderMovie() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    public long inserProviderMovie(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProviderMovie(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProviderMovie(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

}
