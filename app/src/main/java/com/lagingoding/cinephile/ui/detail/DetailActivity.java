package com.lagingoding.cinephile.ui.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.lagingoding.cinephile.BuildConfig;
import com.lagingoding.cinephile.R;
import com.lagingoding.cinephile.model.offline.DataColumns;
import com.lagingoding.cinephile.model.offline.MovieHelper;
import com.lagingoding.cinephile.model.remote.Movie;
import com.lagingoding.cinephile.model.remote.TV;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lagingoding.cinephile.model.offline.DatabaseContract.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_detail_posterFilm)
    ImageView ivDetailPosterFilm;
    @BindView(R.id.tv_detail_titleFilm)
    TextView tvDetailTitleFilm;
    @BindView(R.id.tv_detail_overviewFilm)
    TextView tvDetailOverviewFilm;
    @BindView(R.id.tv_detail_rating)
    TextView tvDetailRating;
    @BindView(R.id.btn_addToWatchList)
    Button btnDetailFavorite;

    private MovieHelper movieHelper;
    private Boolean isFavorite = false;
    private Movie movie;
    private TV tv;
    private int idFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        tv = getIntent().getParcelableExtra("response_tv_show");
        movie = getIntent().getParcelableExtra("response");

        if (tv != null) {
            idFavorite = tv.getId();
        } else {
            idFavorite = movie.getId();
        }

        setupDetail(tv, movie);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + idFavorite),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        setFavorite();
    }

    private void setupDetail(TV tv, Movie movie) {
        if (tv != null) {
            setupDataTV(tv);
        } else {
            setupData(movie);
        }
    }

    private void setupDataTV(TV tv) {
        tvDetailTitleFilm.setText(tv.getOriginalName());
        tvDetailOverviewFilm.setText(tv.getOverview());
        tvDetailRating.setText(String.valueOf(tv.getVoteCount()));
        Glide.with(this)
                .load(BuildConfig.IMAGE_LINK_ORIGINAL + tv.getPosterPath())
                .into(ivDetailPosterFilm);
    }

    private void setupData(Movie movie) {
        tvDetailTitleFilm.setText(movie.getOriginalTitle());
        tvDetailOverviewFilm.setText(movie.getOverview());
        tvDetailRating.setText(String.valueOf(movie.getVoteCount()));
        Glide.with(this)
                .load(BuildConfig.IMAGE_LINK_W500 + movie.getPosterPath())
                .into(ivDetailPosterFilm);
    }

    @OnClick(R.id.btn_addToWatchList)
    public void addFavorite(View view) {
        movie = getIntent().getParcelableExtra("response");
        tv = getIntent().getParcelableExtra("response_tv_show");
        if (isFavorite) {
            if (movie != null) {
                removeFavorite(movie);
            } else {
                removeFavoriteTV(tv);
            }
        } else {
            if (movie != null) {
                saveFavorite(movie);
            } else {
                saveFavoriteTV(tv);
            }
        }
        isFavorite = !isFavorite;
        setFavorite();
    }

    private void saveFavoriteTV(TV tv) {
        ContentValues cv = new ContentValues();
        cv.put(DataColumns.COLUMN_MOVIE_ID, tv.getId());
        cv.put(DataColumns.COLUMN_POSTER, tv.getPosterPath());
        cv.put(DataColumns.COLUMN_BACKDROP, tv.getBackdropPath());
        cv.put(DataColumns.COLUMN_MOVIE_TITLE, tv.getOriginalName());
        cv.put(DataColumns.COLUMN_OVERVIEW, tv.getOverview());
        cv.put(DataColumns.COLUMN_RELEASE_DATE, tv.getFirstAirDate());
        cv.put(DataColumns.COLUMN_VOTE, tv.getVoteCount());

        getContentResolver().insert(CONTENT_URI, cv);
        Toast.makeText(DetailActivity.this,
                getString(R.string.added_favorite),
                Toast.LENGTH_LONG).show();
    }

    private void removeFavoriteTV(TV tv) {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + tv.getId()),
                null,
                null
        );
    }

    private void setFavorite() {
        if (isFavorite) {
            btnDetailFavorite.setText(getString(R.string.removeToWatchList));
        } else {
            btnDetailFavorite.setText(getString(R.string.add_to_watch_list));
        }
    }

    private void removeFavorite(Movie movie) {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + movie.getId()),
                null,
                null
        );

    }

    private void saveFavorite(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(DataColumns.COLUMN_MOVIE_ID, movie.getId());
        cv.put(DataColumns.COLUMN_POSTER, movie.getPosterPath());
        cv.put(DataColumns.COLUMN_BACKDROP, movie.getBackdropPath());
        cv.put(DataColumns.COLUMN_MOVIE_TITLE, movie.getOriginalTitle());
        cv.put(DataColumns.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(DataColumns.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        cv.put(DataColumns.COLUMN_VOTE, movie.getVoteAverage());

        getContentResolver().insert(CONTENT_URI, cv);
        Toast.makeText(DetailActivity.this,
                getString(R.string.added_favorite),
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
