package com.progamermobilnih.movie;


import com.progamermobilnih.movie.Pojo.GenrePojo;
import com.progamermobilnih.movie.Pojo.MoviePojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String MOVIE_BASE_URL =  "https://api.themoviedb.org/3/";


    @GET("movie/popular")
    Call<MoviePojo> getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviePojo> getTopRated(@Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<GenrePojo> getGenreApi(@Query("api_key") String apiKey);

    @GET("movie/{id}/{similar}")
    Call<MoviePojo> getSimilar(@Path("id") int id, @Path("similar") String similar, @Query("api_key") String apiKey);








}
