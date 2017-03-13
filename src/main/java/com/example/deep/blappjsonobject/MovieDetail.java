package com.example.deep.blappjsonobject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.widget.TextView;
import android.widget.Toast;

public class MovieDetail extends ActionBarActivity implements AbsListView.OnScrollListener {
    private static final int MAX_ROWS = 15;
    private int lastTopValue = 0;
    private List<String> modelList = new ArrayList<>();
    private ListView listView;
    private ImageView backgroundImage;
    private ArrayAdapter adapter;
    CheckBox fav;
    TextView display;
    SQLiteDatabase sqldb;
    String tablcontain;
    String val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        listView = (ListView) findViewById(R.id.list);
        Intent i = getIntent();
        final String movie_name = i.getStringExtra("movienamexxx");
        String movie_year = i.getStringExtra("movieyearxxx");
        String movie_director = i.getStringExtra("moviedirectorxxx");
        String movie_plot = i.getStringExtra("movieplotxxx");
        String movie_actor = i.getStringExtra("movieactorxxx");
        String movie_genre = i.getStringExtra("moviegenrexxx");
        String movie_writer = i.getStringExtra("moviewriterxxx");
        String movie_language = i.getStringExtra("movielanguagexxx");
        String movie_imdbrating = i.getStringExtra("movieimdbratingxxx");
        String movie_country = i.getStringExtra("moviecountryxxx");
        String movie_awards = i.getStringExtra("movieawardsxxx");
        modelList.add("Movie :  " + movie_name);
        modelList.add("Year :  " + movie_year);
        modelList.add("Director :  " + movie_director);
        modelList.add("Plot :  " + movie_plot);
        modelList.add("Actors :  " + movie_actor);
        modelList.add("Genre :  " + movie_genre);
        modelList.add("Writer :  " + movie_writer);
        modelList.add("Language :  " + movie_language);
        modelList.add("ImdbRating :  " + movie_imdbrating);
        modelList.add("Country :  " + movie_country);
        modelList.add("Awards :  " + movie_awards);


        adapter = new ArrayAdapter(this, R.layout.list_row, modelList);

        listView.setAdapter(adapter);
        sqldb = openOrCreateDatabase("movie.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        try {
            tablcontain = "CREATE TABLE IF NOT EXISTS tbl ("
                    + "movie TEXT primary key,"
                    + "favstatus TEXT);";

            sqldb.execSQL(tablcontain);


        } catch (Exception e) {
            Toast.makeText(MovieDetail.this, "ERROR " + e.toString(), Toast.LENGTH_LONG).show();
        }


        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.custom_header, listView, false);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.fav, listView, false);
        listView.addHeaderView(header, null, false);
        listView.addFooterView(footer, null, false);
        fav = (CheckBox) findViewById(R.id.checkBox);
        display = (TextView) findViewById(R.id.favorite);


        fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (fav.isChecked() == true) {
                    val = "on";
                    String query = "INSERT or replace INTO tbl(movie,favstatus)VALUES('" + movie_name.toString() + "','" + val + "')";
                    sqldb.execSQL(query);
                }
                else {
                    val = "off";
                    String query = "INSERT or replace INTO tbl(movie,favstatus)VALUES('" + movie_name.toString() + "','" + val + "')";
                    sqldb.execSQL(query);
                }
            }
        });

        backgroundImage = (ImageView) header.findViewById(R.id.listHeaderImage);
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        backgroundImage.setImageBitmap(bmp);
        listView.setOnScrollListener(this);
    }


    @Override

    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }


    @Override

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        Rect rect = new Rect();
        backgroundImage.getLocalVisibleRect(rect);
        if (lastTopValue != rect.top) {
            lastTopValue = rect.top;
            backgroundImage.setY((float) (rect.top / 2.0));
        }
    }
}
