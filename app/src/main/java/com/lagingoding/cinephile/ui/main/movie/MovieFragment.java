package com.lagingoding.cinephile.ui.main.movie;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lagingoding.cinephile.R;
import com.lagingoding.cinephile.model.DataMovies;
import com.lagingoding.cinephile.model.remote.Movie;
import com.lagingoding.cinephile.util.base.BaseViewMovie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment implements BaseViewMovie {

    @BindView(R.id.rv_home_movie)
    RecyclerView rvHomeMovie;
    @BindView(R.id.pb_home_movie)
    ProgressBar pbHomeMovie;

    private RecyclerView.Adapter adapter;
    private MoviePresenter presenter;
    private ArrayList<Movie> list = new ArrayList<>();
    private final String STATE_LIST = "state_list";

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOrientation(savedInstanceState);
    }

    private void setupMVP() {
        presenter = new MoviePresenter(this);
        presenter.getNowPlaying();
    }

    @Override
    public void showData(DataMovies dataMovies) {
        if (dataMovies != null) {
            rvHomeMovie.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new MovieAdapter(dataMovies.getMovies(), getContext());
            rvHomeMovie.setAdapter(adapter);
            list.addAll(dataMovies.getMovies());
        } else {
            pbHomeMovie.setVisibility(View.GONE);
        }
    }

    private void setupOrientation(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setupMVP();
        } else {
            hideProgress();
            list = savedInstanceState.getParcelableArrayList(STATE_LIST);
            adapter = new MovieAdapter(list, getContext());
            rvHomeMovie.setAdapter(adapter);
            rvHomeMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideProgress() {
        pbHomeMovie.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_LIST, list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rvHomeMovie.setAdapter(null);
        presenter = null;
        adapter = null;
    }
}
