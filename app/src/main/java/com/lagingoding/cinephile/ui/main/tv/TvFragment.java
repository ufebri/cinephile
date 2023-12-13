package com.lagingoding.cinephile.ui.main.tv;


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
import com.lagingoding.cinephile.model.DataTV;
import com.lagingoding.cinephile.model.remote.TV;
import com.lagingoding.cinephile.util.base.BaseViewTV;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvFragment extends Fragment implements BaseViewTV {

    @BindView(R.id.rv_home_tv)
    RecyclerView rvHomeTv;
    @BindView(R.id.pb_home_tv)
    ProgressBar pbHomeTv;

    private RecyclerView.Adapter adapter;
    private TvPresenter presenter;
    private ArrayList<TV> list = new ArrayList<>();
    private final String STATE_LIST = "state_list";

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orientationSetup(savedInstanceState);
    }

    private void setupMVP() {
        presenter = new TvPresenter(this);
        presenter.getTvShow();
    }

    @Override
    public void showData(DataTV dataTV) {
        if (dataTV != null) {
            rvHomeTv.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new TvAdapter(dataTV.getTv(), getContext());
            rvHomeTv.setAdapter(adapter);
            list.addAll(dataTV.getTv());
        } else {
            pbHomeTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideProgress() {
        pbHomeTv.setVisibility(View.GONE);
    }

    private void orientationSetup(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setupMVP();
        } else {
            hideProgress();
            list = savedInstanceState.getParcelableArrayList(STATE_LIST);
            adapter = new TvAdapter(list, getContext());
            adapter.notifyDataSetChanged();
            rvHomeTv.setLayoutManager(new LinearLayoutManager(getContext()));
            rvHomeTv.setAdapter(adapter);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_LIST, list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
        adapter = null;
        rvHomeTv.setAdapter(null);
    }
}
