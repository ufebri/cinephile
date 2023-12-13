package com.lagingoding.cinephile.util.network;


import com.lagingoding.cinephile.model.DataMovies;
import com.lagingoding.cinephile.model.DataTV;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkService {

    @GET("discover/movie")
    Observable<DataMovies> getDiscovery(@Query("api_key") String api_key,
                                        @Query("language") String language);

    @GET("movie/now_playing")
    Observable<DataMovies> getNowPlaying(@Query("api_key") String api_key,
                                         @Query("language") String language);

    @GET("tv/popular")
    Observable<DataTV> getTVPopular(@Query("api_key") String api_key,
                                    @Query("language") String language);

    @GET("search/movie")
    Observable<DataMovies> getResultMovie(@Query("api_key") String api_key,
                                          @Query("language") String language,
                                          @Query("query") String query);

    @GET("search/tv")
    Observable<DataTV> getResultTv(@Query("api_key") String api_key,
                                   @Query("language") String language,
                                   @Query("query") String query);

    @GET("discover/movie")
    Call<DataMovies> getUpcoming(@Query("api_key") String api_key,
                                          @Query("language") String language,
                                          @Query("primary_release_date.gte") String date_gte,
                                          @Query("primary_release_date.lte") String date_lte);
}
