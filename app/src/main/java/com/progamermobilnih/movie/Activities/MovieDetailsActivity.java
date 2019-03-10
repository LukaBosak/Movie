package com.progamermobilnih.movie.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.progamermobilnih.movie.Adapters.MovieAdapter;
import com.progamermobilnih.movie.Api;
import com.progamermobilnih.movie.Pojo.Genre;
import com.progamermobilnih.movie.Pojo.GenrePojo;
import com.progamermobilnih.movie.Pojo.Movie;
import com.progamermobilnih.movie.Pojo.MoviePojo;
import com.progamermobilnih.movie.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView title, overview, releaseDate,genre;
    private ImageView poster, backdrop;

    public static final String POSTER = "poster";
    public static final String TITLE = "title";
    public static final String RELEASE_DATE = "release_date";
    public static final String GENRE = "genre";
    public static final String BACKDROP = "backdrop";
    public static final String OVERVIEW = "overview";
    public static final String MOVIE_ID = "movie_id";


    private List<Genre> genresList = new ArrayList<>();
    private Retrofit retrofit = null;
    private Api api;
    private ArrayList<Integer> genreIds = new ArrayList<>();
    RecyclerView recyclerViewSimilar;
    RecyclerView.LayoutManager layoutManager;
    MovieAdapter adapter;

    int movieId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setRetrofit();
        loadGenre();
        loadSimilar();
        initWidgets();



    }

    private void initWidgets(){

        title = findViewById(R.id.titleDetailsTV);
        overview = findViewById(R.id.overviewTV);
        releaseDate = findViewById(R.id.releaseDateTV);
        genre = findViewById(R.id.genresTV);
        poster = findViewById(R.id.posterImageDetailsIV);
        backdrop = findViewById(R.id.backdropImageDetailsIV);
        recyclerViewSimilar = findViewById(R.id.recyclerViewRVSimilar);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewSimilar.setLayoutManager(layoutManager);
        recyclerViewSimilar.setNestedScrollingEnabled(false);


    }

    private void getData(){

        String genreName = "";
        Intent intent = getIntent();

        if(intent.hasExtra(TITLE)){
            String titleData = intent.getStringExtra(TITLE);
            title.setText(titleData);
        }
        if(intent.hasExtra(POSTER)){
            String posterData = intent.getStringExtra(POSTER);
            Glide.with(this).load(MovieAdapter.IMAGE_BASE_URL + posterData).into(poster);
        }
        if(intent.hasExtra(OVERVIEW)){
            String overviewData = intent.getStringExtra(OVERVIEW);
            overview.setText(overviewData);
        }
        if(intent.hasExtra(RELEASE_DATE)){
            String releaseDateData = intent.getStringExtra(RELEASE_DATE);
            releaseDate.setText(releaseDateData);
        }
        if(intent.hasExtra(BACKDROP)){
            String backdropData = intent.getStringExtra(BACKDROP);
            Glide.with(this).load(MovieAdapter.IMAGE_BASE_URL + backdropData).into(backdrop);
        }
        if (intent.hasExtra(GENRE)){
            genreIds = intent.getIntegerArrayListExtra(GENRE);
        }

        for (int i = 0; i < genreIds.size(); i++){
            for(int j = 0; j < genresList.size(); j++ ){

                if (genreIds.get(i) == genresList.get(j).getId()){
                    genreName = genreName  + genresList.get(j).getName() + " ";
                }
            }
        }
        genre.setText(genreName);


    }

    private void setRetrofit(){

        retrofit = new Retrofit.Builder()
                .baseUrl(Api.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    private void loadGenre(){


        Call<GenrePojo> callGenre = api.getGenreApi(MainActivity.API_KEY);

        callGenre.enqueue(new Callback<GenrePojo>() {

            @Override
            public void onResponse(Call<GenrePojo> call, Response<GenrePojo> response) {

                if(response.isSuccessful() && response.body() != null){
                    genresList = response.body().getGenres();
                }

                getData();
            }

            @Override
            public void onFailure(Call<GenrePojo> call, Throwable t) {
            }
        });

    }

    private void loadSimilar(){

        Intent intent = getIntent();

        if (intent.hasExtra(MOVIE_ID)){
            movieId = intent.getIntExtra(MOVIE_ID,0);
        }
        Call<MoviePojo> call = api.getSimilar(movieId,"similar",MainActivity.API_KEY);

        call.enqueue(new Callback<MoviePojo>() {
            @Override
            public void onResponse(Call<MoviePojo> call, Response<MoviePojo> response) {

                if(response.isSuccessful() && response.body() != null){

                    List<Movie> similarMovies = response.body().getResults();

                    adapter = new MovieAdapter(similarMovies,MovieDetailsActivity.this);
                    recyclerViewSimilar.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<MoviePojo> call, Throwable t) {

            }
        });

    }

}
