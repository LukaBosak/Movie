package com.progamermobilnih.movie;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w400";


    public MovieAdapter(List<Movie> movieList, Context context){
        this.movieList = movieList;
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        String title = movieList.get(i).getTitle();
        String image = IMAGE_BASE_URL + movieList.get(i).getPosterPath();

        myViewHolder.title.setText(title);

        Glide.with(context).load(image).into(myViewHolder.image);



    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageRV);
            title = itemView.findViewById(R.id.titleRV);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(MovieDetailsActivity.TITLE, movieList.get(position).getTitle());
                    intent.putExtra(MovieDetailsActivity.RELEASE_DATE, movieList.get(position).getReleaseDate());
                    intent.putIntegerArrayListExtra(MovieDetailsActivity.GENRE, (ArrayList<Integer>) movieList.get(position).getGenreIds());
                    intent.putExtra(MovieDetailsActivity.POSTER, movieList.get(position).getPosterPath());
                    intent.putExtra(MovieDetailsActivity.BACKDROP, movieList.get(position).getBackdropPath());
                    intent.putExtra(MovieDetailsActivity.OVERVIEW, movieList.get(position).getOverview());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }
    }



}



