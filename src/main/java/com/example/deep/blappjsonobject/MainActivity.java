package com.example.deep.blappjsonobject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView posterxx;
    TextView name;
    TextView year;
    String url="http://www.omdbapi.com/?t=xxx&y=&plot=short&r=json";
    RecyclerView recyclerView;
    MovieAdapter madapter;
    List<Information> data=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        posterxx= (ImageView) findViewById(R.id.posterxxx);
        name= (TextView) findViewById(R.id.namex);
        year= (TextView) findViewById(R.id.yearx);
        madapter=new MovieAdapter(getApplicationContext());
        recyclerView= (RecyclerView) findViewById(R.id.movierecyclerview);
        recyclerView.setAdapter(madapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data=parseJsonResponse(response);
                    madapter.setMovielist(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);



    }
private List<Information> parseJsonResponse(JSONObject response) throws JSONException {
    List<Information> data=new ArrayList<>();

        String movietitle=response.getString(keys.title);
        String movieyear=response.getString(keys.year);
        String movieposter=response.getString(keys.poster);
        String moviedirector=response.getString(keys.director);
        String movieplot=response.getString(keys.plot);
        String movieactor=response.getString(keys.actor);
        String moviegenre=response.getString(keys.genre);
        String moviewriter=response.getString(keys.writer);
        String movielanguage=response.getString(keys.language);
        String movieimdbrating=response.getString(keys.imdbrating);
        String moviecountry=response.getString(keys.country);
        String movieawards=response.getString(keys.awards);
    final Information current=new Information();

   current.poster=movieposter;
        current.name=movietitle;
        current.year=movieyear;
        current.director=moviedirector;
        current.plot=movieplot;
        current.actors=movieactor;
        current.genre=moviegenre;
        current.writer=moviewriter;
        current.language=movielanguage;
        current.imdbrating=movieimdbrating;
        current.coountry=moviecountry;
        current.awards=movieawards;

data.add(current);
    return data;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
}
