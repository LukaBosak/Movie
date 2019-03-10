package com.progamermobilnih.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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


    private List<Genre> genresList = new ArrayList<>();
    private Retrofit retrofit = null;
    private Api api;
    private ArrayList<Integer> genreIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        setRetrofit();
        loadGenre();
        initWidgets();


    }

    private void initWidgets(){

        title = findViewById(R.id.titleDetailsTV);
        overview = findViewById(R.id.overviewTV);
        releaseDate = findViewById(R.id.releaseDateTV);
        genre = findViewById(R.id.genresTV);
        poster = findViewById(R.id.posterImageDetailsIV);
        backdrop = findViewById(R.id.backdropImageDetailsIV);

    }

    private void getData(){

        Intent intent = getIntent();
        String genreName = "";

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


        Call<Pojo> callGenre = api.getGenreApi(MainActivity.API_KEY);

        callGenre.enqueue(new Callback<Pojo>() {

            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {

                if(response.isSuccessful() && response.body() != null){
                    genresList = response.body().getGenres();
                }

                getData();
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
            }
        });

    }



}
