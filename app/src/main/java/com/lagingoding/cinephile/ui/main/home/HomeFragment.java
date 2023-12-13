package com.lagingoding.cinephile.ui.main.home;


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
import com.lagingoding.cinephile.ui.main.movie.MovieAdapter;
import com.lagingoding.cinephile.util.base.BaseViewMovie;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements BaseViewMovie {

    @BindView(R.id.lv_home_discovery)
    RecyclerView lvHomeDiscovery;
    @BindView(R.id.dsv_home_nowPlaying)
    DiscreteScrollView dsvHomeNowPlaying;
    @BindView(R.id.pb_home_discovery)
    ProgressBar pbHomeDiscovery;

    private MovieAdapter nowPlayingAdapter;
    private HomeAdapter homeAdapter;
    private ArrayList<Movie> list = new ArrayList<>();
    private final String STATE_LIST = "state_list";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOrientation(savedInstanceState);
    }

    private void setupMVP() {
        HomePresenter presenter = new HomePresenter(this);
        presenter.getDiscovery();
    }

    @Override
    public void showData(DataMovies dataMovies) {
        if (dataMovies != null) {
            lvHomeDiscovery.setLayoutManager(new LinearLayoutManager(getContext()));
            nowPlayingAdapter = new MovieAdapter(dataMovies.getMovies(), getActivity());
            lvHomeDiscovery.setAdapter(nowPlayingAdapter);

            homeAdapter = new HomeAdapter(dataMovies.getMovies(), getContext());
            dsvHomeNowPlaying.setAdapter(homeAdapter);
            dsvHomeNowPlaying.setItemTransformer(new ScaleTransformer.Builder()
                    .setMaxScale(1.05f)
                    .setMinScale(0.8f)
                    .setPivotX(Pivot.X.CENTER)
                    .setPivotY(Pivot.Y.BOTTOM)
                    .build());
            int n = dsvHomeNowPlaying.getCurrentItem();
            dsvHomeNowPlaying.scrollToPosition(n + 10);


            list.addAll(dataMovies.getMovies());
        } else {
            hideProgress();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideProgress() {
        pbHomeDiscovery.setVisibility(View.GONE);
    }

    private void setupOrientation(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setupMVP();
        } else {
            hideProgress();
            list = savedInstanceState.getParcelableArrayList(STATE_LIST);
            nowPlayingAdapter = new MovieAdapter(list, getContext());
            lvHomeDiscovery.setAdapter(nowPlayingAdapter);
            lvHomeDiscovery.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_LIST, list);
    }
}
