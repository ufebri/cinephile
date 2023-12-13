package com.lagingoding.cinephile.ui.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lagingoding.cinephile.BuildConfig;
import com.lagingoding.cinephile.R;
import com.lagingoding.cinephile.model.remote.Movie;
import com.lagingoding.cinephile.ui.detail.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Movie> response;
    private Context context;

    public HomeAdapter(List<Movie> response, Context context) {
        this.response = response;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_now_playing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitleFilm.setText(response.get(position).getOriginalTitle());
        Glide.with(context)
                .load(BuildConfig.IMAGE_LINK_W500 + response.get(position).getBackdropPath())
                .into(holder.ivPosterFilm);

        Movie item = new Movie();
        item.setId(response.get(position).getId());
        item.setOriginalTitle(response.get(position).getOriginalTitle());
        item.setOverview(response.get(position).getOverview());
        item.setVoteCount(response.get(position).getVoteCount());
        item.setPosterPath(response.get(position).getPosterPath());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailActivity.class)
                        .putExtra("response", item));
            }
        });
    }

    @Override
    public int getItemCount() {
        return response.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_item_nowPlaying)
        ImageView ivPosterFilm;
        @BindView(R.id.tv_titleNowPlaying)
        TextView tvTitleFilm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
