package com.example.deep.blappjsonobject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import java.util.Collections;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyviewHolder> {
    ImageLoader img;
    Mysingleton mysingleton;
    private LayoutInflater inflater;

    List<Information> data= Collections.emptyList();
    String moviedirector;
    String movieplot;
    String  movieactor;
    String moviegenre;
    String moviewriter;
    String movielanguage;
    String movieimdbrating;
    String moviecountry;
    String movieawards;
    public MovieAdapter(Context context){
        inflater=LayoutInflater.from(context);
        mysingleton=Mysingleton.getInstance(context);
        img=mysingleton.getImageLoader();
    }
    public void setMovielist(List<Information> data){
        this.data=data;
    }
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_movie, parent, false);
        MyviewHolder holder=new MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, int position) {
        Information current=data.get(position);
        img.get(current.poster, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.movieposter.setImageBitmap(response.getBitmap());
            }
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        holder.moviename.setText(current.name);
        holder.movieyear.setText(current.year);
        moviedirector=current.director;
        movieplot=current.plot;
        movieactor=current.actors;
        moviegenre=current.genre;
        moviewriter=current.writer;
        movielanguage=current.language;
        movieimdbrating=current.imdbrating;
        moviecountry=current.coountry;
        movieawards=current.awards;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class MyviewHolder extends RecyclerView.ViewHolder{

        ImageView movieposter;
        TextView moviename;
        TextView movieyear;
        private final Context context;
        public MyviewHolder(View itemView) {

            super(itemView);
            context=itemView.getContext();
            moviename= (TextView) itemView.findViewById(R.id.namex);
            movieyear= (TextView) itemView.findViewById(R.id.yearx);
            movieposter= (ImageView) itemView.findViewById(R.id.posterxxx);
            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i=new Intent(context,MovieDetail.class);
                movieposter.buildDrawingCache();
                Bitmap image= movieposter.getDrawingCache();
                final Bundle extras = new Bundle();
                extras.putParcelable("imagebitmap", image);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("movienamexxx", moviename.getText().toString());
                i.putExtra("movieyearxxx", movieyear.getText().toString());
                i.putExtra("moviedirectorxxx", moviedirector);
                i.putExtra("movieplotxxx",movieplot);
                i.putExtra("movieactorxxx",movieactor);
                i.putExtra("moviegenrexxx",moviegenre);
                i.putExtra("moviewriterxxx",moviewriter);
                i.putExtra("movielanguagexxx",movielanguage);
                i.putExtra("movieimdbratingxxx",movieimdbrating);
                i.putExtra("moviecountryxxx",moviecountry);
                i.putExtra("movieawardsxxx",movieawards);
                i.putExtras(extras);
                context.startActivity(i);
             }
});
        }



        }
    }

