package com.lagingoding.cinephile.ui.main.favorite;

import android.content.Intent;
import android.database.Cursor;
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
import com.lagingoding.cinephile.model.remote.TV;
import com.lagingoding.cinephile.ui.detail.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Cursor listMovies;

    public FavoriteAdapter(Cursor items) {
        replaceListMovieItem(items);
    }

    public void replaceListMovieItem(Cursor items) {
        listMovies = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        listMovies.moveToPosition(position);
        holder.bind(new Movie(listMovies));
        holder.bind(new TV(listMovies));
    }

    @Override
    public int getItemCount() {
        if (listMovies != null) {
            return listMovies.getCount();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_titleFilm)
        TextView titleFilm;
        @BindView(R.id.iv_item_posterFilm)
        ImageView imageItem;
        @BindView(R.id.tv_item_overviewFilm)
        TextView overviewItem;
        @BindView(R.id.tv_item_ratingFilm)
        TextView ratingItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Movie movie) {
            titleFilm.setText(movie.getOriginalTitle());
            overviewItem.setText(movie.getOverview());
            ratingItem.setText(String.valueOf(movie.getVoteAverage()));

            Glide.with(itemView)
                    .load(BuildConfig.IMAGE_LINK_W500 + movie.getPosterPath())
                    .into(imageItem);

            Movie item = new Movie();
            item.setId(movie.getId());
            item.setOriginalTitle(movie.getOriginalTitle());
            item.setOverview(movie.getOverview());
            item.setPosterPath(movie.getPosterPath());
            item.setVoteCount(movie.getVoteCount());
            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("response", item);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        void bind(final TV tv) {
            titleFilm.setText(tv.getOriginalName());
            overviewItem.setText(tv.getOverview());
            ratingItem.setText(String.valueOf(tv.getVoteCount()));

            Glide.with(itemView)
                    .load(BuildConfig.IMAGE_LINK_W500 + tv.getPosterPath())
                    .into(imageItem);

            TV item = new TV();
            item.setId(tv.getId());
            item.setOriginalName(tv.getOriginalName());
            item.setOverview(tv.getOverview());
            item.setPosterPath(tv.getPosterPath());
            item.setVoteCount(tv.getVoteCount());
            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("response_tv_show", item);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
