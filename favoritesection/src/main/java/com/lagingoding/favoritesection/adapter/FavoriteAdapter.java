package com.lagingoding.favoritesection.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lagingoding.favoritesection.R;
import com.lagingoding.favoritesection.model.FavoriteModel;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Cursor cursor;

    public FavoriteAdapter(Cursor items) {
        replaceAll(items);
    }

    public void replaceAll(Cursor items) {
        cursor = items;
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
        cursor.moveToPosition(position);
        holder.bind(new FavoriteModel(cursor));
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        else return cursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imgCover;
        TextView tvRating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_titleFilm);
            imgCover = itemView.findViewById(R.id.iv_item_posterFilm);

        }

        void bind(final FavoriteModel favoriteModel) {
            tvTitle.setText(favoriteModel.getOriginalTitle());

            Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500/" + favoriteModel.getPosterPath())
                    .into(imgCover);
        }
    }
}
