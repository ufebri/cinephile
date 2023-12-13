package com.lagingoding.favoritesection.model;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.lagingoding.favoritesection.database.DatabaseContract.getColumnDouble;
import static com.lagingoding.favoritesection.database.DatabaseContract.getColumnInt;
import static com.lagingoding.favoritesection.database.DatabaseContract.getColumnString;
import static com.lagingoding.favoritesection.database.FavoriteTable.COLUMN_BACKDROP;
import static com.lagingoding.favoritesection.database.FavoriteTable.COLUMN_MOVIE_TITLE;
import static com.lagingoding.favoritesection.database.FavoriteTable.COLUMN_OVERVIEW;
import static com.lagingoding.favoritesection.database.FavoriteTable.COLUMN_POSTER;
import static com.lagingoding.favoritesection.database.FavoriteTable.COLUMN_RELEASE_DATE;
import static com.lagingoding.favoritesection.database.FavoriteTable.COLUMN_VOTE;

public class FavoriteModel {
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public FavoriteModel() {
    }

    public FavoriteModel(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.posterPath = getColumnString(cursor, COLUMN_POSTER);
        this.backdropPath = getColumnString(cursor, COLUMN_BACKDROP);
        this.originalTitle = getColumnString(cursor, COLUMN_MOVIE_TITLE);
        this.overview = getColumnString(cursor, COLUMN_OVERVIEW);
        this.releaseDate = getColumnString(cursor, COLUMN_RELEASE_DATE);
        this.voteAverage = getColumnDouble(cursor, COLUMN_VOTE);
    }
}
