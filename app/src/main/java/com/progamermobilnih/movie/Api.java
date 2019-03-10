package com.progamermobilnih.movie;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    String MOVIE_BASE_URL =  "https://api.themoviedb.org/3/";


    @GET("movie/popular")
    Call<MovieResponse> getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRated(@Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<Pojo> getGenreApi(@Query("api_key") String apiKey);





}
