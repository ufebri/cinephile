package com.lagingoding.cinephile.ui.main.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lagingoding.cinephile.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lagingoding.cinephile.model.offline.DatabaseContract.CONTENT_URI;

public class FavoriteFragment extends Fragment {

    @BindView(R.id.rv_favorite)
    RecyclerView rvFavorite;

    private Cursor cursor;
    private FavoriteAdapter adapter;
    private Context context;
    private final String STATE_LIST = "state_list";

    public FavoriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        context = view.getContext();
        adapter = new FavoriteAdapter(cursor);
        rvFavorite.setLayoutManager(new LinearLayoutManager(context));
        rvFavorite.setAdapter(adapter);

        new loadData().execute();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class loadData extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            FavoriteFragment.this.cursor = cursor;
            adapter.replaceListMovieItem(FavoriteFragment.this.cursor);
        }
    }
}
