package com.lagingoding.cinephile.ui.search;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lagingoding.cinephile.R;
import com.lagingoding.cinephile.model.DataMovies;
import com.lagingoding.cinephile.model.DataTV;
import com.lagingoding.cinephile.model.remote.Movie;
import com.lagingoding.cinephile.model.remote.TV;
import com.lagingoding.cinephile.ui.main.movie.MovieAdapter;
import com.lagingoding.cinephile.ui.main.tv.TvAdapter;
import com.lagingoding.cinephile.util.base.BaseViewMT;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements BaseViewMT {

    public static final String KEYWORD_SEARCH = "KEYWORD_SEARCH";

    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.tv_search_keyword)
    TextView tvKeyword;
    @BindView(R.id.btn_search_filter_movies)
    Button btnFilterMovies;
    @BindView(R.id.btn_search_filter_tv)
    Button btnFilterTv;

    private MovieAdapter movieadapter;
    private TvAdapter tvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        String keyword = getIntent().getStringExtra(KEYWORD_SEARCH);
        setupMVP(keyword);
        setupPropertiesView(keyword);
    }

    private void setupPropertiesView(String keyword) {
        tvKeyword.setText(keyword);
    }

    private void setupMVP(String keyword) {
        SearchPresenter presenter = new SearchPresenter(SearchActivity.this);
        presenter.getResultSearchTv(keyword);
        presenter.getResultSearchMovie(keyword);
    }

    @OnClick(R.id.btn_search_filter_tv)
    public void clickTv() {
        showDataMovie(null);
        movieadapter = null;
        startActivity(getIntent());
        tvAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_search_filter_movies)
    public void clickMovie() {
        showDataTv(null);
        tvAdapter = null;
        startActivity(getIntent());
        movieadapter.notifyDataSetChanged();
    }

    @Override
    public void showDataTv(DataTV dataTV) {
        if (dataTV != null) {
            rvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            tvAdapter = new TvAdapter(dataTV.getTv(), SearchActivity.this);
            rvSearch.setAdapter(tvAdapter);
        } else {
            hideProgress();
        }
    }

    @Override
    public void showDataMovie(DataMovies dataMovies) {
        if (dataMovies != null) {
            rvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            movieadapter = new MovieAdapter(dataMovies.getMovies(), SearchActivity.this);
            rvSearch.setAdapter(movieadapter);
        } else {
            hideProgress();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {

    }
}
