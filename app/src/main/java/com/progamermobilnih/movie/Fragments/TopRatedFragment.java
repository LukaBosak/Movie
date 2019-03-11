package com.progamermobilnih.movie.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.progamermobilnih.movie.Activities.MainActivity;
import com.progamermobilnih.movie.Adapters.MovieAdapter;
import com.progamermobilnih.movie.Api;
import com.progamermobilnih.movie.Pojo.Movie;
import com.progamermobilnih.movie.Pojo.MoviePojo;
import com.progamermobilnih.movie.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopRatedFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private Api api;





    public TopRatedFragment() {
        // Required empty public constructor
    }
    public static TopRatedFragment newInstance(String param1, String param2) {
        TopRatedFragment fragment = new TopRatedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setRetrofit();
        loadJson();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top_rated, container, false);

        initWidgets();

        return view;
    }

    private void initWidgets(){

        recyclerView = view.findViewById(R.id.recyclerViewRVTopRated);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

    }

    private void setRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    private void loadJson(){

        Call<MoviePojo> call = api.getTopRated(MainActivity.API_KEY);

        call.enqueue(new Callback<MoviePojo>() {
            @Override
            public void onResponse(Call<MoviePojo> call, Response<MoviePojo> response) {

                if(response.isSuccessful() && response.body() != null){

                    List<Movie> movies = response.body().getResults();

                    adapter = new MovieAdapter(movies,getContext().getApplicationContext());
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<MoviePojo> call, Throwable t) {

            }
        });

    }



}
